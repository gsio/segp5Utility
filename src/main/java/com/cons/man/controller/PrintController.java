package com.cons.man.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.QrVO;
import com.cons.man.domain.RtlsLogVO;
import com.cons.man.domain.SensorLogVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.BeaconService;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.MonitorService;
import com.cons.man.services.NFCService;
import com.cons.man.services.QRService;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;
import com.cons.man.services.WorkerService;
import com.cons.man.services.seg.SegService;
import com.cons.man.util.JsonReader;
import com.cons.man.util.PrintExcel;

import net.sf.json.JSONArray;
import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller(value="PrintController")
public class PrintController {
		
	@Resource(name="SiteService")
	private SiteService siteService;
		
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="WorkerService")
	private WorkerService workerService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="MonitorService")
	private MonitorService monitorService;
	
	@Resource(name="DeviceService")
	private DeviceService deviceService;	

	@Resource(name="NFCService")
	private NFCService nfcService;
	
	@Resource(name="BeaconService")
	private BeaconService beaconService;
	
	@Resource(name="QRService")
	private QRService qrService;
	
	@Resource(name="SegService")
	private SegService segService;

	@RequestMapping(value = {"/printWorkerList"})
	public void printWorkerList(HttpSession session , Model model, HttpServletRequest request,
		HttpServletResponse response, @RequestParam(value="cont_id", defaultValue="-1")int cont_id) {
		
		//System.out.println("[Print - Worker] Cont: " + cont_id);
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		List<WorkerVO> workerList;
	
		if(userInfo.getCont_type() == 1){
			workerList = workerService.getWorkRecord(userInfo.getCompany_id(), userInfo.getSite_id(), userInfo.getCont_id());			
		}
		else {
			if(cont_id > 0) {
				workerList = workerService.getWorkRecord(userInfo.getCompany_id(), userInfo.getSite_id(), cont_id);
			}
			else {
				workerList = workerService.getWorkRecord(userInfo.getCompany_id(), userInfo.getSite_id(), -1);	
			}	
		}
		PrintExcel.INSTANCE.printWorkerList(request, response, workerList);	
	}

	@RequestMapping(value = {"/printUserList"})
	public void printUserList(HttpSession session , Model model, HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id) {
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
		List<UserVO> userList;		
		
		if(userInfo.getCont_type() == 1) {
			userList = userService.getUserList(userInfo.getSite_id(), userInfo.getCont_id());		
		}
		else {
			if(cont_id > 0) {
				userList = userService.getUserList(userInfo.getSite_id(), cont_id);
			}
			else {
				userList = userService.getUserList(userInfo.getSite_id(), -1);	
			}
			
		}		
		PrintExcel.INSTANCE.printUserList(request, response, userList);
	}
	
	@RequestMapping(value = {"/printBeaconList"})
	public void printBeaconList(HttpSession session, Model model,
		HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id)
	{		
		//System.out.println("[printBeaconList]: " + site_id + "/" + cont_id);
		List<BeaconVO> list = beaconService.getBeaconListByCont(site_id, cont_id);		
		PrintExcel.INSTANCE.printBeaconList(request, response, list);		
	}	
	
	@RequestMapping(value = {"/printNfcList"})
	public void printNfcList(HttpSession session, Model model,
		HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id)
	{		
		List<NFCVO> list = nfcService.getNFCListByCont(site_id, cont_id);		
		PrintExcel.INSTANCE.printNfcList(request, response, list);		
	}	
	
	
	@RequestMapping(value = {"/printSensorList"})
	public void printSensorList(HttpSession session , Model model, HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="date", defaultValue="")String date,
		@RequestParam(value="section", defaultValue="-1")int section) 
	{			
		ArrayList<SensorLogVO> sensorList = new ArrayList<SensorLogVO>();
		try {
			JSONObject json = JsonReader.readJsonFromUrl("http://13.209.31.139:11243/getSensorLogList?input_date=" + date + "&input_section=" + section);
			//JSONObject json = JsonReader.readJsonFromUrl("http://211.212.221.98:11243/getSensorLogList?input_date=" + date + "&input_section=" + section);
			JSONParser jsonParser = new JSONParser();
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			try {
				jsonArray = (org.json.simple.JSONArray)jsonParser.parse(json.get("sensorList").toString());
				//System.out.println("[Print - Sensor Log]: " + jsonArray.size());
				if(jsonArray.size() > 0) {					
					for(Object object : jsonArray) {
						org.json.simple.JSONObject vo = (org.json.simple.JSONObject) object;
						SensorLogVO log = new SensorLogVO();
						log.setAlias((String) vo.get("alias"));
						log.setSection_name((String) vo.get("section_name"));
						log.setO2(Double.parseDouble(String.valueOf(vo.get("o2"))));
						log.setCo2(Double.parseDouble(String.valueOf(vo.get("co2"))));
						log.setCo(Double.parseDouble(String.valueOf(vo.get("co"))));
						log.setH2s(Double.parseDouble(String.valueOf(vo.get("h2s"))));
						log.setLel(Double.parseDouble(String.valueOf(vo.get("ch4"))));
						log.setTime_diff_min(Integer.parseInt(String.valueOf(vo.get("time_diff_min"))));
						log.setWrite_time((String) vo.get("write_time"));
						sensorList.add(log);
					}
					PrintExcel.INSTANCE.printSensorList(request, response, sensorList, date);
				}
			} catch (Exception e) {
				//System.out.println("[Print - Sensor Log] Error: " + e);
				PrintExcel.INSTANCE.printSensorList(request, response, sensorList, date);
			}
		} catch (Exception e) {
			//System.out.println("[Print - Sensor Log] Error: " + e);
			PrintExcel.INSTANCE.printSensorList(request, response, sensorList, date);
		}
	}	
	
	@RequestMapping(value = {"/printRtls"})
	public void printRtls(HttpSession session , Model model, HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="")int site_id,		
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id,
		@RequestParam(value="date", defaultValue="")String date)
	{
		/*
		System.out.println("[Print - RTLS Log] site_id: " + site_id);
		System.out.println("[Print - RTLS Log] cont_id: " + cont_id);
		System.out.println("[Print - RTLS Log] date: " + date);
		*/		
		ArrayList<RtlsLogVO> rtlsList = new ArrayList<RtlsLogVO>();
		
		try {		
			JSONObject json = JsonReader.readJsonFromUrl("http://13.209.31.139:11243/getRtlsEngineerLogList/" + site_id + "/" + cont_id + "/" + date);
			//JSONObject json = JsonReader.readJsonFromUrl("http://211.212.221.98:11243/getRtlsEngineerLogList/" + site_id + "/" + cont_id + "/" + date);

			JSONParser jsonParser = new JSONParser();
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			try {
				jsonArray = (org.json.simple.JSONArray)jsonParser.parse(json.get("rtlsList").toString());
				//System.out.println("[Print - RTLS Log]: " + jsonArray.size());
				
				if(jsonArray.size() > 0) {					
					for(Object object : jsonArray) {
						org.json.simple.JSONObject vo = (org.json.simple.JSONObject) object;
						RtlsLogVO log = new RtlsLogVO();
						log.setCont_name((String) vo.get("cont_name"));
						log.setWt_name((String) vo.get("wt_name"));
						log.setName((String) vo.get("name"));
						log.setSection(Integer.parseInt(String.valueOf(vo.get("section"))));
						log.setDevice_idx(Integer.parseInt(String.valueOf(vo.get("device_idx"))));
						log.setSection_name((String) vo.get("section_name"));
						log.setRtls_type((String) vo.get("rtls_type"));
						log.setInout_type((String) vo.get("inout_type"));
						log.setWrite_time((String) vo.get("write_time"));
						rtlsList.add(log);
					}
					PrintExcel.INSTANCE.printLocationList(request, response, rtlsList, date);					
				}
			} catch (Exception e) {
				//System.out.println("[Error - RTLS Log 1]: " + e);
				PrintExcel.INSTANCE.printLocationList(request, response, rtlsList, date);	
			}
			
		} catch (Exception e) {
			//System.out.println("[Error - RTLS Log 2]: " + e);
			PrintExcel.INSTANCE.printLocationList(request, response, rtlsList, date);	
		}		
	}
	
	@RequestMapping(value = {"/printInout"})
	public void printInout(HttpSession session , Model model, HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="")int site_id,		
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id,
		@RequestParam(value="date", defaultValue="")String date, 
		@RequestParam(value="cont_name", defaultValue="")String cont_name)
	{
		/*
		System.out.println("[Print - RTLS Log] site_id: " + site_id);
		System.out.println("[Print - RTLS Log] cont_id: " + cont_id);
		System.out.println("[Print - RTLS Log] date: " + date);
		*/		
		ArrayList<RtlsLogVO> inoutList = new ArrayList<RtlsLogVO>();
		
		try {		
			JSONObject json = JsonReader.readJsonFromUrl("http://13.209.31.139:11243/getSectionInoutLogList/" + site_id + "/" + cont_id + "/" + date);
			//JSONObject json = JsonReader.readJsonFromUrl("http://211.212.221.98:11243/getSectionInoutLogList/" + site_id + "/" + cont_id + "/" + date);

			JSONParser jsonParser = new JSONParser();
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			try {
				jsonArray = (org.json.simple.JSONArray)jsonParser.parse(json.get("inoutList").toString());
				//System.out.println("[Print - Inout Log]: " + jsonArray.size());
				
				if(jsonArray.size() > 0) {					
					for(Object object : jsonArray) {
						org.json.simple.JSONObject vo = (org.json.simple.JSONObject) object;
						RtlsLogVO log = new RtlsLogVO();
						log.setSection(Integer.parseInt(String.valueOf(vo.get("section"))));				
						log.setSection_name((String) vo.get("section_name"));		
						log.setAccess_count(Integer.parseInt(String.valueOf(vo.get("access_count"))));
						inoutList.add(log);
					}
					PrintExcel.INSTANCE.printInoutList(request, response, inoutList, date, cont_name);					
				}
			} catch (Exception e) {
				//System.out.println("[Error - Inout Log 1]: " + e);
				PrintExcel.INSTANCE.printInoutList(request, response, inoutList, date, cont_name);	
			}
			
		} catch (Exception e) {
			//System.out.println("[Error - Inout Log 2]: " + e);
			PrintExcel.INSTANCE.printInoutList(request, response, inoutList, date, cont_name);	
		}	
		
	}
	
	@RequestMapping(value = {"/printQRLog"})
	public void printQRLog(HttpSession session, Model model,
		HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="date", defaultValue="")String date)
	{		
		System.out.println("[Print - QR Log] site_id: " + site_id + "/" + date);
		List<QrVO> list = qrService.getQRInoutLogList(site_id, date);	
		PrintExcel.INSTANCE.printQRLogList(request, response, list, date);		
	}	
	
}


