package com.omni.curis.helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class ImageHelper {
	
	 private static String uploadfolder = "./src/main/resources/static/img/product/";
	 private static String catche = "./src/main/resources/static/img/imageproduct/";
	
	public String addproductimage(MultipartFile[] uploadfile)
	   {
		   try {
	   
			   String imagename=ProductHelper.generateUniqueId()+"";
			   Path filepath = Paths.get(catche, imagename+".jpg");
	           OutputStream os = Files.newOutputStream(filepath);
	               os.write(uploadfile[0].getBytes());
	               os.close();
	           return imagename;

	     } catch (IOException e) {
	    	 return "";
	        
	     }
		 }


	   private static BufferedImage resize(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
		   BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		    Graphics2D g = resizedImage.createGraphics();
		    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		    g.dispose();

		    return resizedImage;
	   }

}
