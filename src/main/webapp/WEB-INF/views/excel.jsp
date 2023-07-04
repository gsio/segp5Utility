<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
 <head>

 <style type="text/css">
 /* @page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;
	mso-page-orientation:landscape;} */
 </style>
 </head>
 <%     
//java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");	
//String today = formatter.format(new java.util.Date());
request.setCharacterEncoding("utf-8");
String gubun = request.getParameter("gubun");
String appendix = request.getParameter("appendix");

String title ="";
if(gubun.equals("risk_eval"))
	title="위험성평가";
else if(gubun.equals("risk_reg"))
	title="위험성평가등록부";
else if(gubun.equals("workerList"))
	title="출역일보";
else if(gubun.equals("measureList"))
	title="점검및조치이력";
else if(gubun.equals("riskInspectList"))
	title="위험성평가점검일지";
title = java.net.URLEncoder.encode(title, "UTF-8");
String file_name = title + "_" + appendix;	
String ExcelName  = file_name + ".xls";	     



response.setHeader("Content-Disposition", "attachment; filename="+ExcelName);
response.setContentType("application/vnd.xls")	; 
//response.sendRedirect(request.getContextPath() + "/riskList");
%>

  <body marginwidth="0" marginheight="0">
 
 <% out.print(request.getParameter("print_body")); %>
 </body>
 </html>
