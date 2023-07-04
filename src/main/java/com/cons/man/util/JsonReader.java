package com.cons.man.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
	  StringBuilder sb = new StringBuilder();
	  int cp;
	  while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
	  }
	  return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	  InputStream is = new URL(url).openStream();
	  try {
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		  String jsonText = readAll(rd);
		  JSONObject json = new JSONObject(jsonText);
    
		  return json;
	  } finally {
		  is.close();
	  }
  }
  
/*
  public static void main(String[] args) throws IOException, JSONException, ParseException {
	  double lat = 37 ;
	  double lon = 128;
    JSONObject json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?lat=" +lat + "&lon=" + lon + "&units=metric&appid=b231606340553d9174136f7f083904b3&APPID=da6db9aad545136ce8708eb2d76c2559");
    
    JSONArray array =  new JSONArray(json.get("weather").toString());
    
    
    System.out.println(array);
    System.out.println(json.get("id"));
    
    
	//JSONObject weather = json.getJSONObject("weather");
    
	
	
  }*/
}