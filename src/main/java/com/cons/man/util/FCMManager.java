package com.cons.man.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FCMManager {
	public static FCMManager INSTANCE = new FCMManager();
	public final String FCM_SERVER_APIKEY = "AAAAo_KXE0M:APA91bG8Jmcp9DdqHaFjD9xbqeOp8s3Byr8T1JvDuEWk6FzwPWydetLtl76iWRrSq26xIZ98HErFLZkrRa6y2Rlnl82wmxh-CSj7OiRtPQrJoV-yozH-0DwUK7Sg9ng0VtlhZkf44Bit";
	public final String FCM_SERVER_URL = "https://fcm.googleapis.com/fcm/send";
	public enum FCM_TOPICS{SEG_ALERT, SEJ_ALERT};
	
	public String sendFCM(String input_msg) throws UnsupportedEncodingException, IOException {
		URL url = new URL(FCM_SERVER_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + FCM_SERVER_APIKEY);
        conn.setDoOutput(true);
        
        //TODO : push용메시지/ 실제 알림메시지구분해서 보내주기
      //  String input = "{\"data\": {\"title\":\"ALERT\",\"msg\":\"" + push_msg + "\",\"app_obj\":" + json_obj + "}, \"to\" :\"/topics/" + topic.toString() + "\"}";
    
        System.out.println(input_msg);
        OutputStream os = conn.getOutputStream();
 
        os.write(input_msg.toString().getBytes("UTF-8"));
        os.flush();
        os.close();

    //    int responseCode = conn.getResponseCode();
 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer stb = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
        	stb.append(inputLine);
        }
        in.close();
      
        return stb.toString();
	}
	
	public String sendFCMByTopic(FCM_TOPICS topic, String push_msg, org.json.JSONObject json_obj) throws IOException {		
        //TODO : push용메시지/ 실제 알림메시지구분해서 보내주기
        String input = "{\"data\": {\"title\":\"ALERT\",\"msg\":\"" + push_msg + "\",\"app_obj\":" + json_obj + "}, \"to\" :\"/topics/" + topic.toString() + "\"}";
        return sendFCM(input);      
	}
	public void sendFCMToGroup(String push_msg, org.json.JSONObject json_obj, List<String> fcm_list) throws IOException {		
		//fcm list 호출
		for(String fcm_token : fcm_list) {
			String input = "{\"data\": {\"title\":\"ALERT\",\"msg\":\"" + push_msg + "\",\"app_obj\":" + json_obj + "}, \"to\" :\"" + fcm_token + "\"}";
			String result = sendFCM(input);
			System.out.println(result);
		}
		
		//String input = "{\"data\": {\"title\":\"ALERT\",\"msg\":\"" + push_msg + "\",\"app_obj\":" + json_obj + "}, \"to\" :\"/topics/" + topic.toString() + "\"}";
		//return sendFCM(input);
		
	}
	
	public String sendFCMByTopic2(FCM_TOPICS topic, String fullmsg) throws IOException {
		
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + FCM_SERVER_APIKEY);
		conn.setDoOutput(true);
		String input = fullmsg;
		System.out.println(input);
		OutputStream os = conn.getOutputStream();
		
		os.write(input.toString().getBytes("UTF-8"));
		os.flush();
		os.close();
		
		//    int responseCode = conn.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer stb = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			stb.append(inputLine);
		}
		in.close();
		
		return stb.toString();
		
	}	
}
