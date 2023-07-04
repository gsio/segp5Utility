package com.cons.man.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cons.man.domain.FileVO;
import com.cons.man.persistence.FileMapper;
import com.cons.man.util.FileWriter.PARENT_TYPE;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class FileWriter {	
	
	public static FileWriter INSTANCE = new FileWriter();
	
	private FileOutputStream fos;
	
	public static final String PATH = "D:\\server\\upload";
	public static final String UPLOAD_PATH = "D:\\server\\upload\\excel";
	public static final String APK_PATH = "D:\\server\\upload\\apk";
	
	public enum PARENT_TYPE {
		EQUIP, BLANK1, NOTICE, BLANK3, BLANK4, BLANK5;
		private static PARENT_TYPE[] allValues = values();
		public static PARENT_TYPE fromOrdinal(int n) {return allValues[n];}
	} 
	
	public boolean deleteFile(String fileName){
		File file = new File(PATH + "/" + fileName);
		return file.delete();
	}
	
	public void writeFile(MultipartFile file, String fileName) {
		try{	         
			byte fileData[] = file.getBytes();             
			fos = new FileOutputStream(PATH + "/" + fileName); 
	        fos.write(fileData);	         
		} catch(Exception e){	             
			e.printStackTrace();             
		} finally{	             
			if(fos != null){	                 
				try{
					fos.close();
				} catch(Exception e){}
			}
		}
	}
	
	public int getImageForExcel(Workbook wb, String image_path) {
	
		   int pictureIndex = -1;
	       InputStream fis = null;
	       ByteArrayOutputStream bos = null;

	       try {	   
	    	   boolean isNo = true;	    	   
	    	   File file = null;
	   			if(image_path != null && !image_path.equals("")) {
	   				file = new File(FileWriter.PATH + "//" + image_path);
	 	    	   
	 	    	   if(!file.exists()) {
	 	    		   isNo = true;
	 	    	   }
	 	    	   	
	   			}
	   		    
	   			if(isNo) {
	   				file = new File(FileWriter.PATH + "//" + "noimage.png");
 				}
    	   
	   			BufferedImage image = ImageIO.read(new URL(image_path));
				int img_width = image.getWidth(null);
				int img_height = image.getHeight(null);			    
		
				//buffer로 변환
				BufferedImage buf_image = createResizedCopy(image, 200, 200, true);
				// 아직까지 특정상황이 한가지밖에 안나왔음 2021-04-23		
	   			
	   			//System.out.println("Image Path: " + image_path + " / [Orientation]: " + getOrientation(file));
	            if(getOrientation(file) != 1) {
	            	buf_image = rotateImage(buf_image, 90); // orientation 6 처리
	            }
	            
	   			ByteArrayOutputStream os = new ByteArrayOutputStream();
	   			ImageIO.write(buf_image, "png", os); 
	   			fis = new ByteArrayInputStream(os.toByteArray());
	           
	   			bos = new ByteArrayOutputStream( );
	   			int cc = 0;
	   			while ( (cc = fis.read()) != -1) {
	   				bos.write( cc );
	   			}
	   			pictureIndex = wb.addPicture( bos.toByteArray(), Workbook.PICTURE_TYPE_PNG  );
	       } catch(Exception e){e.printStackTrace();}
	       finally {
	    	   try{
	           if (fis != null) fis.close();
	           if (bos != null) bos.close();
	    	   }catch(Exception e){}
	       }	       
	       return pictureIndex;
	}			
	
	public MultipartFile getImageForGsilDir(String image_path) {
		MultipartFile multipartFile = null;
		try {			
			File file = null;
			FileItem fileItem = null;
			InputStream fis = null;
			
			if(image_path != null && !image_path.equals("")) {
				file = new File(FileWriter.UPLOAD_PATH + "//" + image_path);		
				if(file.exists()) {	 	 
					//System.out.println("[getImageForGsilDir] File is exists");
					fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
	 	    		fis = new FileInputStream(file);
	 	    		OutputStream os = fileItem.getOutputStream();
	 	    		IOUtils.copy(fis, os);
	 	    		multipartFile = new CommonsMultipartFile(fileItem);	 	    		
				}
				else {
					//System.out.println("[getImageForGsilDir] File is Null");
				}
			}	
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		return multipartFile;	       
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
	
	public List<FileVO> getFileList(int parent_id, PARENT_TYPE type, FileMapper mapper) {
		
		FileVO vo = new FileVO();
		
		//System.out.println("[File] ("+type+") > getFileList - parent_id: " + parent_id + " 번 정보 보여주기");
		
		vo.setParent_id(parent_id);
		vo.setType(type.ordinal());
		
		List<FileVO> list = mapper.getFileList(vo);
		
		return list;
	}
	
	public String insertFileDetail(PARENT_TYPE type, FileVO vo, int parent_id, int idx, FileMapper mapper){
		if(vo.getFile() != null && vo.getFile().getSize() > 0) {
			vo.setParent_id(parent_id);				
			String virtName = type.name() + "_" + UUID.randomUUID().toString() +"_" + parent_id;
			vo.setVirtname(type.name() + "/" + virtName);
			FileWriter.INSTANCE.writeFile(vo.getFile(), type.name() + "/" + virtName);
			vo.setOrgname(vo.getFile().getOriginalFilename());
			vo.setContent_type(vo.getFile().getContentType());
			vo.setFile_size(vo.getFile().getSize());
			
			vo.setIdx(idx);
			vo.setType(type.ordinal());
			mapper.insertFile(vo);
			
			return type.name() + "/" + virtName;
		}
		return null;
	}
	
	public void updateFileDetail(PARENT_TYPE type, FileVO vo, int parent_id, int idx, FileMapper mapper) {	
		FileWriter.INSTANCE.deleteFile(vo.getVirtname());
		mapper.deleteFile(vo.getVirtname());
		insertFileDetail(type, vo, parent_id, idx, mapper);
	}
}
