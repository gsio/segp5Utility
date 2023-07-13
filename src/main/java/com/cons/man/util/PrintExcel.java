package com.cons.man.util;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.QrVO;
import com.cons.man.domain.RtlsLogVO;
import com.cons.man.domain.SensorLogVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;

public class PrintExcel {
	public static PrintExcel INSTANCE = new PrintExcel();
	public enum CELL_TYPE {BORDER_GREY_TH};

	public void printExcel(String file_name, HttpServletRequest request, HttpServletResponse response , Workbook wb){		
		
		String sFileName = "";
		if(wb.getClass() == HSSFWorkbook.class) {
			sFileName = file_name + ".xls";
		}
		else {//XSSF, SXSFFF
			sFileName = file_name + ".xlsx";
		}
		
		OutputStream fileOut = null;

		try {
			
			response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.

			String strClient = request.getHeader("User-Agent");
			String fileName = sFileName;
			sFileName = new String (sFileName.getBytes("KSC5601"), "8859_1");
			fileName = URLDecoder.decode( "\"" + new String(fileName.getBytes( "UTF-8" ), "8859_1" ) + "\"", "8859_1" );
			
			if (strClient.indexOf("MSIE 5.5") > -1) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			}
			else if(strClient.indexOf("iPhone") > -1) {				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");				
			}
			else {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			}
	
			fileOut = response.getOutputStream(); 
			wb.write(fileOut);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}			
	}

	public void setHardDiskImgToExcel(HttpServletRequest request, Sheet s, Workbook wb, String virtname, int rownum_start, int cellnum_start, int rownum_end, int cellnum_end){
		if(virtname == null || virtname.equals("")) {
			
		}else{
			String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			{
				int pictureIndex = FileWriter.INSTANCE.getImageForExcel(wb, path + "/image_thumb?virtname=" + virtname);
				if(pictureIndex >= 0 ){
					
					if(wb.getClass() == HSSFWorkbook.class) {
						HSSFPatriarch patriarch = (HSSFPatriarch)s.createDrawingPatriarch();
					    HSSFClientAnchor anchor = new HSSFClientAnchor(70,10,950,250,(short) cellnum_start, (short) rownum_start,(short) cellnum_end, (short)rownum_end); // 이미지 크기조절은 여기서..
					    anchor.setAnchorType( ClientAnchor.DONT_MOVE_AND_RESIZE );
					    patriarch.createPicture(anchor, pictureIndex);	
					}else {
						Drawing drawing = s.createDrawingPatriarch();							
					    XSSFClientAnchor anchor = new XSSFClientAnchor(70,10,950,250,(short) cellnum_start, (short) rownum_start,(short) cellnum_end, (short)rownum_end); // 이미지 크기조절은 여기서..
					    anchor.setAnchorType( ClientAnchor.DONT_MOVE_AND_RESIZE );
					    drawing.createPicture(anchor, pictureIndex);	
					}
						
				}
			    
			}
		}
		
		
	}
	
	/**images 폴더에 있는 이미지 **/
	public void setImagesFolderImgToExcel(HttpServletRequest request, Sheet s, Workbook wb, String images_path, int rownum_start, int cellnum_start, int rownum_end, int cellnum_end){
		setImagesFolderImgToExcel(request, s, wb, images_path, rownum_start, cellnum_start,rownum_end,cellnum_end, 70, 10 , 950, 250);
	}
	
	//**dy1, dy2 => 0 ~ 255**/
	public void setImagesFolderImgToExcel(HttpServletRequest request, Sheet s, Workbook wb, String images_path, int rownum_start, int cellnum_start, int rownum_end, int cellnum_end, int dx1, int dy1, int dx2, int dy2){
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		{
			int pictureIndex = FileWriter.INSTANCE.getImageForExcel(wb, path + "" + images_path);
			try {
				if(pictureIndex >= 0 ){
				    HSSFPatriarch patriarch = (HSSFPatriarch)s.createDrawingPatriarch();
				    HSSFClientAnchor anchor = new HSSFClientAnchor(dx1,dy1,dx2,dy2,(short) cellnum_start, (short) rownum_start,(short) cellnum_end, (short)rownum_end); // 이미지 크기조절은 여기서..
				    anchor.setAnchorType( ClientAnchor.DONT_MOVE_AND_RESIZE );
				    patriarch.createPicture(anchor, pictureIndex);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	

    /**
     * ex) cell = makeCellStyle(wb, 2, 1, HSSFCellStyle.BORDER_THIN, HSSFColor.WHITE.index, false, 12, "바탕체", false , true);
     * @param wb workbook
     * @param align ALIGN 0: GENERAL, 1: LEFT , 2: CENTER , 3: RIGHT, 4: FILL , 5: JUSTIFY or HSSFCellStyle.ALIGN_CENTER
     * @param v_align VERTICAL_ALIGN 0: TOP ,1 : ,  2: CENTER , 3: BOT or HSSFCellStyle.VERTICAL_CENTER
     * @param border_type ex)HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THICK, HSSFCellStyle.BORDER_NONE
     * @param foreground_color ex)HSSFColor.WHITE.index
     * @param iswraptext true : 자동줄바꿈 , false : 줄바꿈 X
     * @param font_height 글자 크기 (pt)
     * @param font_family 글자체 ex)"바탕체"
     * @param font_under underbar
     * @param font_bold bold
     * 
     */
	public CellStyle makeCellStyle(Workbook wb, int align,int v_align, short border_type, 
				short foreground_color, boolean iswraptext, int font_height, String font_family, boolean font_under, boolean font_bold ){
		CellStyle cell = wb.createCellStyle();
		cell.setAlignment((short)align);
		cell.setVerticalAlignment((short)v_align);	// 세로 정렬 중단
		Font fh = wb.createFont();
		if(font_bold) fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		if(font_under) fh.setUnderline(Font.U_SINGLE);		
		fh.setFontName(font_family);
		fh.setFontHeight((short)(font_height * 20));
		
		cell.setFont(fh);
		cell.setBorderRight(border_type);              //테두리 설정    
		cell.setBorderLeft(border_type);    
		cell.setBorderTop(border_type);    
		cell.setBorderBottom(border_type); 
		if(foreground_color > -1){
			cell.setFillForegroundColor(foreground_color);     //색 설정
			cell.setFillPattern(CellStyle.SOLID_FOREGROUND);     //색 패턴 설정
		}
		cell.setWrapText(iswraptext);//자동줄바꿈
		
		return cell;
		
	}
	
	public CellStyle makeCellStyleByTYPE( Workbook wb, CELL_TYPE type){
		CellStyle cell = wb.createCellStyle();; 
		switch(type){
		case BORDER_GREY_TH:
			cell = makeCellStyle(wb, 2, 1, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, false, 12, "바탕체", false , true);
			break;
		}
		return cell;
		
	}
	
	public String getCurrentDate(){
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String cur_date = sdf.format(d); //현재날짜 string으로 
		return cur_date;
	}
	
	private void makeHSSFDefaultRow(Sheet s, Row r ,int rownum, int line_height, int max_column, CellStyle style, String[] text){
		r = s.createRow(rownum);
		r.setHeight((short)(line_height * 15));
		for(int i = 0; i < max_column ; i++){
			r.createCell( i).setCellStyle(style);
			r.getCell( i).setCellValue(new HSSFRichTextString(text[i]));
		}
	}
	
	public void printWorkerList(HttpServletRequest request, HttpServletResponse response, List<WorkerVO> workerList) {
		int base_height = 30;
		HSSFWorkbook wb = new HSSFWorkbook();
		
		CellStyle td_title = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 20, "HY신명조", true, true);
		CellStyle td0 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 9, "굴림", false, false);		
		CellStyle th = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 10, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
		
		Sheet s = wb.createSheet("근로자 목록");;
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);
	
		String[] th_arr = new String[]{"NO","이름","성별","소속","직종","주민번호","채용일","전화번호","혈액형","신규교육","밀폐교육","사진"};
		s.setColumnWidth(0, 30 * 37);//no
		s.setColumnWidth(1, 62 * 37);//성명
		s.setColumnWidth(2, 40 * 37);//성별
		s.setColumnWidth(3, 110 * 37);//소속
		s.setColumnWidth(4, 90 * 37);//직종
		s.setColumnWidth(5, 150 * 37);//주민번호
		s.setColumnWidth(6, 110 * 37);//채용일
		s.setColumnWidth(7, 90 * 37);//전화번호
		s.setColumnWidth(8, 50 * 37);//혈액형
		s.setColumnWidth(9, 110 * 37);//신규교육
		s.setColumnWidth(10, 120 * 37);//밀폐교육
		s.setColumnWidth(11, 80 * 37);//사진
	
		final int MAX_COLUMN = 12;

		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 *15));
		r.createCell(0). setCellValue (new HSSFRichTextString ("근로자 이력사항"));
		r.getCell(0).setCellStyle(td0);
		for(int i = 1 ; i <= 11 ; i++){
			r.createCell(i).setCellStyle(td0);
		}
		s.addMergedRegion (new CellRangeAddress((int) 0 , (short)0 , (int) 0, (short)MAX_COLUMN - 1)); 
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
		int idx = 1;
		Iterator<WorkerVO> it = workerList.iterator();
			while(it.hasNext()){
				WorkerVO vo = it.next();
				r = s.createRow(s.getLastRowNum() + 1);
				r.setHeight((short)(71 * 15));
				
				r. createCell( 0). setCellValue (new HSSFRichTextString (idx++ +""));
				r.getCell(0).setCellStyle(td1);
				r. createCell( 1). setCellValue (new HSSFRichTextString (vo.getName()));
				r.getCell(1).setCellStyle(td1);
				
				String gen = "남";
				if(vo.getJumin().length() > 6 ){
					if( vo.getJumin().substring(6,7).equals('1')) gen ="남";
					else if(vo.getJumin().substring(6,7).equals('2')) gen ="여";
					else if(vo.getJumin().substring(6,7).equals('5')) gen ="남";
					else if(vo.getJumin().substring(6,7).equals('6')) gen ="여";
				}
				
				
				r. createCell( 2). setCellValue (new HSSFRichTextString (gen));
				r.getCell(2).setCellStyle(td1);
				r. createCell( 3). setCellValue (new HSSFRichTextString (vo.getCont_name()));
				r.getCell(3).setCellStyle(td1);
				r. createCell( 4). setCellValue (new HSSFRichTextString (vo.getT_name()));
				r.getCell(4).setCellStyle(td1);	
				
				String jumin = vo.getJumin();
				
				if(jumin.length() > 6 )
					jumin = vo.getJumin().substring(0,6) + "-" +vo.getJumin().substring(6,7) + "******";
				

				r. createCell( 5). setCellValue (new HSSFRichTextString (jumin));
				r.getCell(5).setCellStyle(td1);	
				
		
				r. createCell( 6). setCellValue (new HSSFRichTextString (vo.getHiredate())); 
				r.getCell(6).setCellStyle(td1);
				String phone = "";
				
				if(vo.getPhone()!=null && vo.getPhone().length() == 11 )
					phone = vo.getPhone().substring(0,3) + "-" + vo.getPhone().substring(3,7) + "-" + vo.getPhone().substring(7,11);
				else if(vo.getPhone()!=null && vo.getPhone().length() == 10 )
					phone = vo.getPhone().substring(0,3) + "-" + vo.getPhone().substring(3,6) + "-" + vo.getPhone().substring(6,10);
				else
					phone = vo.getPhone();
				
				r. createCell( 7). setCellValue (new HSSFRichTextString (phone));
				r.getCell(7).setCellStyle(td1);
				
				r. createCell( 8). setCellValue (new HSSFRichTextString (vo.getBtype()));
				r.getCell(8).setCellStyle(td1);
		
				r. createCell( 9). setCellValue (new HSSFRichTextString (vo.getEdudate()));
				r.getCell(9).setCellStyle(td1);
								
				String sealed_date1 = "";
				String sealed_date2 = "";
				String sealed_date3 = "";
				String sealed_date4 = "";
				
				if(vo.getSealed_date1() != null) {
					if(vo.getSealed_date1().length() >= 8) {
						sealed_date1 = "1차 : " + vo.getSealed_date1();
					}
				}
				
				if(vo.getSealed_date2() != null) {
					if(vo.getSealed_date2().length() >= 8) {
						sealed_date2 = "2차 : " + vo.getSealed_date2();
					}
				}
				
				if(vo.getSealed_date3() != null) {
					if(vo.getSealed_date3().length() >= 8) {
						sealed_date3 = "3차 : " + vo.getSealed_date3();
					}
				}
				
				if(vo.getSealed_date4() != null) {
					if(vo.getSealed_date4().length() >= 8) {
						sealed_date4 = "4차 : " + vo.getSealed_date4();
					}
				}
				
				r. createCell( 10). setCellValue (new HSSFRichTextString (
							sealed_date1
							+"\r\n" + sealed_date2
							+"\r\n" + sealed_date3
							+"\r\n" + sealed_date4
				));
				r.getCell(10).setCellStyle(td1);		
				
				
				r. createCell( 11). setCellValue (new HSSFRichTextString (""));
				r.getCell(11).setCellStyle(td1);	
				
				if(vo.getEduimage() != null && !vo.getEduimage().equals("") ){					
					PrintExcel.INSTANCE.setHardDiskImgToExcel(request, s, wb,  vo.getEduimage(), s.getLastRowNum(), 11, s.getLastRowNum(), 11);
					
				}
			}

			/**파일명**/
			String file_name = "근로자 이력관리 [" + getCurrentDate() +"]";
			printExcel(file_name, request, response, wb);
		
	}

	public void printUserList(HttpServletRequest request, HttpServletResponse response, List<UserVO> userList) {
		int base_height = 30;
		HSSFWorkbook wb = new HSSFWorkbook();
		
		CellStyle td_title = makeCellStyle(wb, CellStyle.ALIGN_CENTER,  CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 20, "HY신명조", true, true);
		CellStyle td0 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 9, "굴림", false, false);
		CellStyle th = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 10, "굴림", false, true);
			
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
		
		String prev_workdate= "";	

		Sheet s = wb.createSheet("관리자 목록");;
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);
			
		String[] th_arr = new String[]{"NO","업체명","이 름","ID","직책","권한","연락처","혈액형","밀폐교육", "사진"};
		s.setColumnWidth(0, 35 * 37);//no
		s.setColumnWidth(1, 110 * 37);//업체명
		s.setColumnWidth(2, 62 * 37);//이름
		s.setColumnWidth(3, 70 * 37);//ID
		s.setColumnWidth(4,	60 * 37);//직책
		s.setColumnWidth(5, 150 * 37 );//권한
		s.setColumnWidth(6, 90 * 37);//연락처
		s.setColumnWidth(7, 55 * 37);//혈액형
		s.setColumnWidth(8, 130 * 37);//밀폐교육
		s.setColumnWidth(9, 110 * 37);//사진

		final int MAX_COLUMN = 10;
				
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 *15));
		r. createCell( 0). setCellValue (new HSSFRichTextString ("관리자 목록"));
		r. getCell(0).setCellStyle(td0);
		for(int i = 1 ; i <= 9 ; i++){
			r. createCell( i).setCellStyle(td0);
		}
		s.addMergedRegion (new CellRangeAddress(( int) 0 , ( short )0 , ( int) 0, (short )9 )); 
		
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);		
	
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		 
		Iterator<UserVO> it = userList.iterator();
		int idx = 1;
		while(it.hasNext()){				
			UserVO vo = it.next();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(71 * 15));
			
			r. createCell( 0). setCellValue (new HSSFRichTextString (idx++ +""));
			r.getCell(0).setCellStyle(td1);
			r. createCell( 1). setCellValue (new HSSFRichTextString (vo.getCont_name()));
			r.getCell(1).setCellStyle(td1);
			r. createCell( 2). setCellValue (new HSSFRichTextString (vo.getName()));
			r.getCell(2).setCellStyle(td1);
			r. createCell( 3). setCellValue (new HSSFRichTextString (vo.getUserid()));
			r.getCell(3).setCellStyle(td1);
			r. createCell( 4). setCellValue (new HSSFRichTextString (vo.getGrade()));
			r.getCell(4).setCellStyle(td1);
			r. createCell( 5). setCellValue (new HSSFRichTextString (vo.getRole_name()));
			r.getCell(5).setCellStyle(td1);
			r. createCell( 6). setCellValue (new HSSFRichTextString (vo.getPhone()));
			r.getCell(6).setCellStyle(td1);
			r. createCell( 7). setCellValue (new HSSFRichTextString (vo.getBtype()));
			r.getCell(7).setCellStyle(td1);
			
			String sealed_date1 = "";
			String sealed_date2 = "";
			String sealed_date3 = "";
			String sealed_date4 = "";
			
			if(vo.getSealed_date1() != null) {
				if(vo.getSealed_date1().length() >= 8) {
					sealed_date1 = "1차 : " + vo.getSealed_date1();
				}
			}
			
			if(vo.getSealed_date2() != null) {
				if(vo.getSealed_date2().length() >= 8) {
					sealed_date2 = "2차 : " + vo.getSealed_date2();
				}
			}
			
			if(vo.getSealed_date3() != null) {
				if(vo.getSealed_date3().length() >= 8) {
					sealed_date3 = "3차 : " + vo.getSealed_date3();
				}
			}
			
			if(vo.getSealed_date4() != null) {
				if(vo.getSealed_date4().length() >= 8) {
					sealed_date4 = "4차 : " + vo.getSealed_date4();
				}
			}
			
			r. createCell(8). setCellValue (new HSSFRichTextString (
						sealed_date1
						+"\r\n" + sealed_date2
						+"\r\n" + sealed_date3
						+"\r\n" + sealed_date4
			));		
			
			r.getCell(8).setCellStyle(td1);
			r. createCell( 9). setCellValue (new HSSFRichTextString ());
			r.getCell(9).setCellStyle(td1);
			
			if(vo.getPhoto() != null && !vo.getPhoto().equals("") ){				
				PrintExcel.INSTANCE.setHardDiskImgToExcel(request, s, wb,  vo.getPhoto(), s.getLastRowNum(), 9, s.getLastRowNum(), 9); 
			}
		}
	
		String file_name = "관리자 이력관리 [" + getCurrentDate() +"]";
		printExcel(file_name, request, response, wb);
		
	}

	public void printBeaconList(HttpServletRequest request, HttpServletResponse response, List<BeaconVO> list) {		
		int base_height = 30;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 5;
		
		Iterator<BeaconVO> it = list.iterator();
		
		String prev_workdate= "";
	
		Sheet s = wb.createSheet("비콘배정목록");;
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO","업체명","이름","구분","비콘번호"};
		s.setColumnWidth(0, 80 * 37);
		s.setColumnWidth(1, 230 * 37);
		s.setColumnWidth(2, 100 * 37);
		s.setColumnWidth(3, 100 * 37);
		s.setColumnWidth(4, 120 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString("비콘 배정 목록"));
		for(int i = 1 ; i <= 4; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int)0, (short)0, (int)0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
			
		
		int idx = 1;
		while(it.hasNext()){
			BeaconVO vo = it.next();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));		
			r. createCell( 1).setCellStyle(td1);
			r. getCell( 1).setCellValue(new HSSFRichTextString(vo.getCont_name()));		
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getName()));		
			String role = "";
			if(vo.getRole() == 1) {
				role = "관리자";
			}
			else if(vo.getRole() == 2) {
				role = "근로자";
			} else {
				role = "미배정";
			}			
			r. createCell( 3).setCellStyle(td1);
			r. getCell( 3).setCellValue(new HSSFRichTextString(role));		

			r. createCell( 4).setCellStyle(td1);
			r. getCell( 4).setCellValue(new HSSFRichTextString(vo.getIdx() + "번 비콘"));		
			idx++;
		}
		
		String file_name = "비콘배정목록 [" + getCurrentDate() +"]";
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
		response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
	
		String strClient = request.getHeader("User-Agent");
		String fileName = sFileName;
	
		if (strClient.indexOf("MSIE 5.5") > -1) {
		 //response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
	
		fileOut = response.getOutputStream(); 
		wb.write(fileOut);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}
	
	public void printNfcList(HttpServletRequest request, HttpServletResponse response, List<NFCVO> list) {		
		int base_height = 30;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 5;
		
		Iterator<NFCVO> it = list.iterator();
		
		String prev_workdate= "";
	
		Sheet s = wb.createSheet("NFC배정목록");;
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO","업체명","이름","구분","NFC번호"};
		s.setColumnWidth(0, 80 * 37);
		s.setColumnWidth(1, 230 * 37);
		s.setColumnWidth(2, 100 * 37);
		s.setColumnWidth(3, 100 * 37);
		s.setColumnWidth(4, 120 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString("NFC 배정 목록"));
		for(int i = 1 ; i <= 4; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int)0, (short)0, (int)0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
			
		
		int idx = 1;
		while(it.hasNext()){
			NFCVO vo = it.next();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));		
			r. createCell( 1).setCellStyle(td1);
			r. getCell( 1).setCellValue(new HSSFRichTextString(vo.getCont_name()));		
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getName()));		
			String role = "";
			if(vo.getRole() == 1) {
				role = "관리자";
			}
			else if(vo.getRole() == 2) {
				role = "근로자";
			} else {
				role = "미배정";
			}			
			r. createCell( 3).setCellStyle(td1);
			r. getCell( 3).setCellValue(new HSSFRichTextString(role));		

			r. createCell( 4).setCellStyle(td1);
			r. getCell( 4).setCellValue(new HSSFRichTextString(vo.getIdx() + "번 NFC"));		
			idx++;
		}
		
		String file_name = "NFC배정목록 [" + getCurrentDate() +"]";
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
		response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
	
		String strClient = request.getHeader("User-Agent");
		String fileName = sFileName;
	
		if (strClient.indexOf("MSIE 5.5") > -1) {
		 //response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
	
		fileOut = response.getOutputStream(); 
		wb.write(fileOut);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}
		
	public void printSensorList(HttpServletRequest request, HttpServletResponse response, List<SensorLogVO> list, String input_date) {		
		int base_height = 60;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb,  CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 9;
		
		Iterator<SensorLogVO> it = list.iterator();
		
		String prev_workdate= "";
		
		Sheet s = wb.createSheet(input_date);
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO", "위치", "산소", "이산화탄소", "일산화탄소", "황화수소", "LEL", "딜레이", "시간"};
		s.setColumnWidth(0, 55 * 37);
		s.setColumnWidth(1, 180 * 37);
		s.setColumnWidth(2, 100 * 37);
		s.setColumnWidth(3, 100 * 37);
		s.setColumnWidth(4, 100 * 37);
		s.setColumnWidth(5, 100 * 37);
		s.setColumnWidth(6, 100 * 37);
		s.setColumnWidth(7, 80 * 37);
		s.setColumnWidth(8, 230 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString("금일환경센서"));
		for(int i = 1 ; i <= 8; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int)0, (short)0, (int)0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);			
		
		int idx = 1;
		while(it.hasNext()){
			SensorLogVO vo = it.next();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));			
			
			r. createCell( 1).setCellStyle(td1);

			if(vo.getSection_name() == "" || vo.getSection_name() == null) {
				r. getCell( 1).setCellValue(new HSSFRichTextString("#" + vo.getAlias()));	
			}
			else {
				r. getCell( 1).setCellValue(new HSSFRichTextString("#" + vo.getAlias() + " " + vo.getSection_name()));	
			}						
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getO2() +"%"));		
			r. createCell( 3).setCellStyle(td1);
			r. getCell( 3).setCellValue(new HSSFRichTextString(vo.getCo2() +"ppm"));		
			r. createCell( 4).setCellStyle(td1);
			r. getCell( 4).setCellValue(new HSSFRichTextString(vo.getCo() +"ppm"));	
			r. createCell( 5).setCellStyle(td1);
			r. getCell( 5).setCellValue(new HSSFRichTextString(vo.getH2s() +"ppm"));	
			r. createCell( 6).setCellStyle(td1);
			r. getCell( 6).setCellValue(new HSSFRichTextString(vo.getLel() +"%"));	
			r. createCell( 7).setCellStyle(td1);
			r. getCell( 7).setCellValue(new HSSFRichTextString(vo.getTime_diff_min() +"분"));	
			r. createCell( 8).setCellStyle(td1);
			r. getCell( 8).setCellValue(new HSSFRichTextString(vo.getWrite_time()));			
			idx++;
		}
		
		String file_name = "환경센서 로그 [" + input_date +"]";		
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
		response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
	
		String strClient = request.getHeader("User-Agent");
		String fileName = sFileName;
	
		if (strClient.indexOf("MSIE 5.5") > -1) {
		 //response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
	
		fileOut = response.getOutputStream(); 
		wb.write(fileOut);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}	
	
	public void printLocationList(HttpServletRequest request, HttpServletResponse response, List<RtlsLogVO> list, String input_date) {		
		int base_height = 60;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 9;
		
		Iterator<RtlsLogVO> it = list.iterator();
		
		String prev_workdate= "";
		
		Sheet s = wb.createSheet(input_date);
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO", "업체", "직종", "이름", "위치", "장비", "번호", "출입", "시간"};
		s.setColumnWidth(0, 55 * 37);
		s.setColumnWidth(1, 180 * 37);
		s.setColumnWidth(2, 100 * 37);
		s.setColumnWidth(3, 100 * 37);
		s.setColumnWidth(4, 180 * 37);
		s.setColumnWidth(5, 120 * 37);
		s.setColumnWidth(6, 120 * 37);
		s.setColumnWidth(7, 120 * 37);
		s.setColumnWidth(8, 180 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString("위치파악로그"));
		for(int i = 1 ; i <= 8; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int) 0 , (short)0 , (int) 0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
		
		int idx = 1;
		String name = "";
		while(it.hasNext()){
			RtlsLogVO vo = it.next();
			name = vo.getName();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));		
			r. createCell( 1).setCellStyle(td1);
			r. getCell( 1).setCellValue(new HSSFRichTextString(vo.getCont_name()));		
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getWt_name()));		
			r. createCell( 3).setCellStyle(td1);
			r. getCell( 3).setCellValue(new HSSFRichTextString(vo.getName()));		
			r. createCell( 4).setCellStyle(td1);
			r. getCell( 4).setCellValue(new HSSFRichTextString("#" + vo.getSection() + " " + vo.getSection_name()));
			r. createCell( 5).setCellStyle(td1);
			r. getCell( 5).setCellValue(new HSSFRichTextString(vo.getDevice_idx() + "번"));
			r. createCell( 6).setCellStyle(td1);
			r. getCell( 6).setCellValue(new HSSFRichTextString(vo.getRtls_type()));					
			r. createCell( 7).setCellStyle(td1);
			r. getCell( 7).setCellValue(new HSSFRichTextString(vo.getInout_type()));
			r. createCell( 8).setCellStyle(td1);
			r. getCell( 8).setCellValue(new HSSFRichTextString(vo.getWrite_time().replace(".0", "")));					
			idx++;
		}
		
		String file_name = "위치파악 로그 [" + input_date +"]";
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
			response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
		
			String strClient = request.getHeader("User-Agent");
			String fileName = sFileName;
		
			if (strClient.indexOf("MSIE 5.5") > -1) {
			 //response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			} else {
			 response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			}
		
			fileOut = response.getOutputStream(); 
			wb.write(fileOut);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}
	
	
	public void printInoutList(HttpServletRequest request, HttpServletResponse response, List<RtlsLogVO> list, String input_date, String cont_name) {		
		int base_height = 60;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 16, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 4;
		
		Iterator<RtlsLogVO> it = list.iterator();
		
		String prev_workdate= "";
		
		Sheet s = wb.createSheet(input_date);
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO", "수조번호", "수조명", "금일출역인원수"};
		s.setColumnWidth(0, 55 * 37);
		s.setColumnWidth(1, 120 * 37);
		s.setColumnWidth(2, 180 * 37);
		s.setColumnWidth(3, 80 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString(cont_name + "_구역출입로그"));
		for(int i = 1 ; i <= 4; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int) 0 , (short)0 , (int) 0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
		
		int idx = 1;
		String name = "";
		while(it.hasNext()){
			RtlsLogVO vo = it.next();
			name = vo.getName();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));		
			r. createCell( 1).setCellStyle(td1);
			r. getCell( 1).setCellValue(new HSSFRichTextString(vo.getSection() + "번 수조"));		
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getSection_name()));		
			r. createCell( 3).setCellStyle(td1);
			r. getCell( 3).setCellValue(new HSSFRichTextString(vo.getAccess_count()+ "명"));	
								
			idx++;
		}
		
		String file_name = cont_name + "_구역출입로그 [" + input_date +"]";
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
			response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
		
			String strClient = request.getHeader("User-Agent");
			String fileName = sFileName;
		
			if (strClient.indexOf("MSIE 5.5") > -1) {
			 //response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			} else {
			 response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			}
		
			fileOut = response.getOutputStream(); 
			wb.write(fileOut);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}
	
	public void printQRLogList(HttpServletRequest request, HttpServletResponse response, List<QrVO> list, String input_date) {		
		int base_height = 60;
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		CellStyle td_title = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM, HSSFColor.WHITE.index, true, 32, "HY중고딕", false, true);
		CellStyle td1 = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.WHITE.index, true, 14, "돋움체", false, false);
		CellStyle th = makeCellStyle(wb, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, CellStyle.BORDER_THIN, HSSFColor.GREY_25_PERCENT.index, true, 14, "굴림", false, true);
	
		Font fh = wb.createFont();
		fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fh.setFontName("굴림");
		fh.setFontHeight((short)(12 * 20));
		fh.setColor(HSSFColor.WHITE.index);
	
		int MAX_COLUMN = 5;
		
		Iterator<QrVO> it = list.iterator();
		
		String prev_workdate= "";
		
		Sheet s = wb.createSheet(input_date);
		Row r = null;
		s.setMargin(Sheet.TopMargin, 0.3);
		s.setMargin(Sheet.BottomMargin, 0.3);
		s.setMargin(Sheet.LeftMargin, 0.2);
		s.setMargin(Sheet.RightMargin, 0.2);	
		
		String[] th_arr = new String[]{"NO", "업체", "이름", "출입", "시간"};
		s.setColumnWidth(0, 55 * 37);
		s.setColumnWidth(1, 180 * 37);
		s.setColumnWidth(2, 100 * 37);
		s.setColumnWidth(3, 120 * 37);
		s.setColumnWidth(4, 180 * 37);
		
		//제목
		r = s.createRow(0);
		r.setHeight((short)(90 * 15));
		r.createCell( 0).setCellStyle(td_title);
		r.getCell( 0).setCellValue(new XSSFRichTextString("QR출입로그"));
		for(int i = 1 ; i <= 5; i++){
			r.createCell(i).setCellStyle(td_title);
		}
		s.addMergedRegion (new CellRangeAddress((int) 0 , (short)0 , (int) 0, (short)MAX_COLUMN - 1));	
	
		makeHSSFDefaultRow(s,r, s.getLastRowNum() + 1, base_height, MAX_COLUMN, th, th_arr);
		
		int idx = 1;
		String name = "";
		while(it.hasNext()){
			QrVO vo = it.next();
			name = vo.getName();
			r = s.createRow(s.getLastRowNum() + 1);
			r.setHeight((short)(40 * 15));		
			r. createCell( 0).setCellStyle(td1);
			r. getCell( 0).setCellValue(new HSSFRichTextString(idx + ""));		
			r. createCell( 1).setCellStyle(td1);
			r. getCell( 1).setCellValue(new HSSFRichTextString(vo.getCont_name()));		
			r. createCell( 2).setCellStyle(td1);
			r. getCell( 2).setCellValue(new HSSFRichTextString(vo.getName()));		
			
			r. createCell( 3).setCellStyle(td1);
			if(vo.getInout_type() == 1) {
				r. getCell( 3).setCellValue(new HSSFRichTextString("입장"));		
			}
			else {
				r. getCell( 3).setCellValue(new HSSFRichTextString("퇴장"));
			}
			r. createCell( 4).setCellStyle(td1);
			r. getCell( 4).setCellValue(new HSSFRichTextString(vo.getWrite_time()));					
			idx++;
		}
		
		String file_name = "[" + input_date +"] QR출입로그";
		String sFileName = file_name + ".xlsx";
		try {
			sFileName = new String ( sFileName.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		OutputStream fileOut = null;
	
		try{
			response.reset();  // 이 문장이 없으면 excel 등의 파일에서 한글이 깨지는 문제 발생.
		
			String strClient = request.getHeader("User-Agent");
			String fileName = sFileName;
		
			if (strClient.indexOf("MSIE 5.5") > -1) {
			 //response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			} else {
			 response.setContentType("application/vnd.ms-excel");
			 response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			}
		
			fileOut = response.getOutputStream(); 
			wb.write(fileOut);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			wb.dispose();
			try { wb.close(); } catch(Exception ignore) {}
		}
	}
	
}
