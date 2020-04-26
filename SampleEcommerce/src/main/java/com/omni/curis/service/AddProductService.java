package com.omni.curis.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.omni.curis.aws.EntryPointS3;
import com.omni.curis.entity.Product;
import com.omni.curis.helper.ProductHelper;
import com.omni.curis.repository.ProductRepo;




@Service
public class AddProductService {
	
	@Autowired
	ProductRepo productRepo;
	 
	@Autowired
	EntryPointS3 entryPointS3;
	
	
	@Value("${app.uploadfolder}")
	 private  String uploadfolder;
	
	@Value("${app.catche}")
	 private  String catche ;
	
	 Pageable pageable = PageRequest.of(0, 8);
	 public boolean editproduct(String usedid,String imagename,String category,String subcategory,String size,String name,String origprice,String offerprice,String longdescription,int id)
		{
			try
			{
				
				Product product=productRepo.findById(id);
				if(imagename!=null && !imagename.isEmpty())
				product.setImageurl(savetos3(imagename,500, 775));
				if(category!=null && !category.isEmpty())
				product.setCategory(category);
				if(longdescription!=null && !longdescription.isEmpty())
				product.setLongdescription(longdescription);
				if(name!=null && !name.isEmpty())
				product.setName(name);
				if(offerprice!=null && !offerprice.isEmpty())
				product.setOfferprice(offerprice);
				if(origprice!=null && !origprice.isEmpty())
				product.setOrigprice(origprice);
				if(size!=null && !size.isEmpty())
				product.setSize(size);
				if(subcategory!=null && !subcategory.isEmpty())
				product.setSubcategory(subcategory);
				if(imagename!=null && !imagename.isEmpty())
				product.setImagename(imagename);
				product.setIsactive(true);
				product.setEntrydate(new Date());
				productRepo.save(product);
				return true;
			}
			
			catch (Exception e) {
				return false;
			}
			
		}

	public boolean addproduct(String usedid,String imagename,String category,String subcategory,String size,String name,String origprice,String offerprice,String longdescription)
	{
		try
		{
			
			Product product=new Product();
			product.setImageurl(savetos3(imagename,500, 775));
			product.setCategory(category);
			product.setLongdescription(longdescription);
			product.setName(name);
			product.setOfferprice(offerprice);
			product.setOrigprice(origprice);
			product.setSize(size);
			product.setSubcategory(subcategory);
			product.setId(ProductHelper.generateUniqueId());
			product.setImagename(imagename);
			product.setIsactive(true);
			product.setEntrydate(new Date());
			productRepo.save(product);
			return true;
		}
		
		catch (Exception e) {
			return false;
		}
		
	}
   public String addproductimage(MultipartFile[] uploadfile)
   {
	   try {
   
		   String imagename=ProductHelper.generateUniqueId()+"";
			  
     	  byte[] bytes = uploadfile[0].getBytes();
           Path path = Paths.get(catche + uploadfile[0].getOriginalFilename());
           Files.write(path, bytes);

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

  
   public Product getsingleproduct(int id)
   {
	   return productRepo.findById(id);
   }
   
   public boolean deleteproduct(int id)
   {
	   try
	   {
		    productRepo.deleteById(id);
		   return true;
	   }catch (Exception e) {
		   return false;
	}
	   
   }
   
   public boolean deactivateProduct(int id)
   {
	   try
	   {
		   Product product=productRepo.findById(id);
		   product.setIsactive(false);
		   productRepo.save(product);
	   }
	   catch (Exception e) {
		
	     }
	 
	   return true;
   }
   
   public boolean activateProduct(int id)
   {
	   try
	   {
		   Product product=productRepo.findById(id);
		   product.setIsactive(true);
		   productRepo.save(product);
	   }
	   catch (Exception e) {
		
	     }
	 
	   return true;
   }
   
	public String savetos3(String imagename,int width,int height) 
	{
		try
		{
			 BufferedImage originalImage = ImageIO.read(new File(catche+imagename+".jpg"));
			 int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		        BufferedImage resizeImageJpg = resize(originalImage, type, width, height);
		        ImageIO.write(resizeImageJpg, "jpg", new File(uploadfolder+ imagename+".jpg")); 

			   }
		catch(Exception e)
		{
		//System.out.println("");
		}
		try
		{
			 File file = new File(catche+imagename+".jpg");
			   if( file.exists())
			      return entryPointS3.savefileintos3(file);
			   else
			   {
				   file = new File(uploadfolder+imagename+".jpg");
				   return entryPointS3.savefileintos3(file);
			   }
		}
		catch (Exception e) {
			return "";
		}
		
		
	}
}
