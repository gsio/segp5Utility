package com.cons.man.controller;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cons.man.util.FileWriter;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Handles requests for the application home page.
 */
@ApiIgnore
@Controller(value = "UtilController")
public class UtilController {
	
	@RequestMapping(value = "/image", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] showImage(HttpServletResponse response, HttpServletRequest request,
		@RequestParam("virtname") String virtname) throws Exception  
	{
		boolean isNo = false;
		File file = null;
		try {		    	
			if(virtname != null && !virtname.equals("")) {		       
				file = new File(FileWriter.PATH + "//" + virtname);
				if(!file.exists())
					isNo = true; 
		    	}else {
		    		isNo = true  ; 
		    	}	
		 
		    	if(isNo) {
		    		file = new File(FileWriter.PATH + "//" + "noimage.png");
		    	}		    		
		    	
		        InputStream is = new FileInputStream(file);
		        try {
					return IOUtils.toByteArray(is);
				}
		        finally {
					IOUtils.closeQuietly(is);
				}	
		    } 
		    catch (IOException e) {
		    	//throw new RuntimeException(e);   
		    }
		return null;	    
	}	
	
	@RequestMapping(value = "/upload_img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] showUploadImg(HttpServletResponse response, HttpServletRequest request,
		@RequestParam("virtname") String virtname) throws Exception  
	{
		boolean isNo = false;
		File file = null;
		try {		    	
			//System.out.println("[showUploadImg]: " + virtname);
			if(virtname != null && !virtname.equals("")) {		       
				file = new File(FileWriter.UPLOAD_PATH + "//" + virtname);
				if(!file.exists())
					isNo = true; 
		    	}else {
		    		isNo = true; 
		    	}	
		 
		    	if(isNo) {
		    		file = new File(FileWriter.PATH + "//" + "noimage.png");
		    	}		    		
		    	
		        InputStream is = new FileInputStream(file);
		        try {
					return IOUtils.toByteArray(is);
				}
		        finally {
					IOUtils.closeQuietly(is);
				}	
		    } 
		    catch (IOException e) {
		    	//throw new RuntimeException(e);   
		    }
		return null;	    
	}	
	
	@RequestMapping(value = "/image_thumb", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] showThumbnailImage(HttpServletResponse response, HttpServletRequest request,
		@RequestParam("virtname") String virtname,
		@RequestParam(value="width", defaultValue="-1")int width, 
		@RequestParam(value="height", defaultValue="-1")int height
	) throws Exception {
		
		boolean isNo = false;
		File file = null;
		
		try {
			if(virtname != null && !virtname.equals("")) {		       
				file = new File(FileWriter.PATH + "//" + virtname);
				if(!file.exists()) {
					isNo = true;
				}		    			 
			} 
			else {
				isNo = true;
			}	
		    	
			if(isNo) {
				file = new File(FileWriter.PATH + "//" + "noimage.png");
			}

			Image image = ImageIO.read(file);
			int img_width = image.getWidth(null);
			int img_height = image.getHeight(null);			    
    
			if(width > 0 && height > 0) {
			
			}
			
			// height만 -1 이고 width가 넘어왔을경우 비율 자체계산
			else if (width <= 0 && height > 0) {
				width  = (int)Math.round((double)height / (double)img_height * img_width);
			}
			
			//width만 -1이고 height가 넘어왔을경우 비율 자체계산
			else if(height <= 0 && width > 0) {
				height = (int)Math.round((double)width / (double)img_width * img_height);
			}
			
			//기본값 적용
			else{
				width = 200;
				height = 200;
			}
			
			//buffer로 변환
			BufferedImage buf_image = createResizedCopy(image, width, height, true);

			// System.out.println("["+ virtname + "]");
			// System.out.println("orientation: " + getOrientation(file));
			
			// 아직까지 특정상황이 한가지밖에 안나왔음 2021-04-23			
            if(getOrientation(file) != 1) {
                buf_image = rotateImage(buf_image, 90); // orientation 6 처리
            }
			
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(buf_image, "gif", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
	             
            try {
            	return IOUtils.toByteArray(is);
            }
            
            finally {
            	IOUtils.closeQuietly(is);
            }
	
		}	
		catch (IOException e) {
			throw new RuntimeException(e);   
		}
	}
	
	// 이미지의 회전에 따라서 특정값을 리턴
	/*
		1. 정상
		2. 좌우반전
		3. 180도 회전
		4. 180도 회전 + 좌우반전
		5. 270도 회전 + 좌우반전
		6. 270도 회전
		7. 90도 회전 + 좌우반전
		8. 90도 회전	
	*/
	private int getOrientation(File file) throws Exception {
		int orientation = 1;
		Metadata metadata;
		Directory directory;

		try {
			metadata = ImageMetadataReader.readMetadata(file);
			directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if(directory != null) {
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
			}

		} catch (ImageProcessingException e) {
			System.err.println("[ImgUtil] could not process image");
			e.printStackTrace();
		} catch (MetadataException e) {
			System.err.println("[ImgUtil] could not get orientation from image");
			e.printStackTrace();
		}

		return orientation;
	}
	
	private BufferedImage rotateImage (BufferedImage bimage, int radians) {
		BufferedImage newImage;
		if(radians == 90 || radians == 270) {
			newImage = new BufferedImage(bimage.getHeight(),bimage.getWidth(),bimage.getType());
		} 
		
		else if (radians==180){
			newImage = new BufferedImage(bimage.getWidth(),bimage.getHeight(),bimage.getType());
		}
		
		else{
			return bimage;
		}
		
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.rotate(Math.toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
		graphics.translate((newImage.getWidth() - bimage.getWidth()) / 2, (newImage.getHeight() - bimage.getHeight()) / 2);
		graphics.drawImage(bimage, 0, 0, bimage.getWidth(), bimage.getHeight(), null);

		return newImage;
	}
	
	
	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
	    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
	    Graphics2D g = scaledBI.createGraphics();
	    if (preserveAlpha) {
	        g.setComposite(AlphaComposite.Src);
	    }
	    g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
	    g.dispose();
	    return scaledBI;
	}
	
	@RequestMapping(value = "/file/app", method = RequestMethod.GET)
	@ResponseBody
	public void showFileApp(HttpServletResponse response, HttpServletRequest request,
		@RequestParam("virtname") String virtname, 
		@RequestParam("orgname") String orgname,
		@RequestParam("content_type") String content_type
	) throws Exception  {
		
		File file = new File(FileWriter.APK_PATH + "//" + virtname);	       	
		FileInputStream inputStream = new FileInputStream(file);
	    
		if (content_type == null) {	    
			content_type = "application/octet-stream";
		}

		String header =request.getHeader("User-Agent");
		String docName;
		if (header.contains("MSIE") || header.contains("Trident") || header.contains("Edge")) {
			docName = URLEncoder.encode(orgname,"UTF-8").replaceAll("\\+", "%20");
		}
		else{
			docName = new String(orgname.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\""); response.setContentType(content_type);
		response.setContentLength((int) file.length());	 
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
	    
		OutputStream outStream = response.getOutputStream();
		
		byte[] buffer = new byte[10240];
		int bytesRead = -1;
	 
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
	 
		response.reset();
	        
		inputStream.close();
		outStream.close();
		outStream.flush();
	}	 
	
	
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	@ResponseBody
	public void showFile(HttpServletResponse response, HttpServletRequest request,
		@RequestParam("virtname") String virtname, 
		@RequestParam("orgname") String orgname,
		@RequestParam("content_type") String content_type
	) throws Exception  {
		
		File file = new File(FileWriter.PATH + "//" + virtname);	       	
		FileInputStream inputStream = new FileInputStream(file);
	    
		if (content_type == null) {	    
			content_type = "application/octet-stream";
		}

		String header =request.getHeader("User-Agent");
		String docName;
		if (header.contains("MSIE") || header.contains("Trident") || header.contains("Edge")) {
			docName = URLEncoder.encode(orgname,"UTF-8").replaceAll("\\+", "%20");
		}
		else{
			docName = new String(orgname.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		System.out.println("[File]: " + docName);		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\""); response.setContentType(content_type);
		response.setContentLength((int) file.length());	 
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		OutputStream outStream = response.getOutputStream();
		
		byte[] buffer = new byte[10240];
		int bytesRead = -1;
	 
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
	        
		inputStream.close();
		outStream.close();
		outStream.flush();
	}	 
	
}
