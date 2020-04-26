package com.omni.curis.controller;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.omni.curis.entity.DAOUser;
import com.omni.curis.security.JwtUserDetailsService;
import com.omni.curis.service.AddProductService;


@RestController
public class AdminProductController {
	
	@Autowired
	AddProductService addProductService;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
		
	 @PostMapping(value="/deleteproduct/{id}")
	 public ResponseEntity<?> deleteproduct(HttpServletRequest request,@PathVariable String id){
	  
	  final String requestTokenHeader = request.getHeader("Authorization");
	  DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
	  if(daouser!=null)
	  {
		  addProductService.deleteproduct(Integer.parseInt(id));
			 return new ResponseEntity<>("product deleted successfully",HttpStatus.OK);
	  }
	  else
		  return new ResponseEntity<>("Please Logout and Login again to continue our Services.", HttpStatus.UNAUTHORIZED);	
    } 
	 
	
    @PostMapping(value="/deactiveyourproduct/{id}")
		 public ResponseEntity<?> deactiveyourproduct(HttpServletRequest request,@PathVariable String id){
		  
		  final String requestTokenHeader = request.getHeader("Authorization");
		  DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		  if(daouser!=null)
		  {
			  addProductService.deactivateProduct(Integer.parseInt(id));
				 return new ResponseEntity<>("product successfully deactivated.",HttpStatus.OK);
		  }
		  else
			  return new ResponseEntity<>("Please Logout and Login again to continue our Services.", HttpStatus.UNAUTHORIZED);	
	 } 
	 @PostMapping(value="/activateproduct/{id}")
		 public ResponseEntity<?> activateproduct(HttpServletRequest request,@PathVariable String id){
		  
		  final String requestTokenHeader = request.getHeader("Authorization");
		  DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		  if(daouser!=null)
		  {
			  addProductService.activateProduct(Integer.parseInt(id));
			  return new ResponseEntity<>("product successfully activated.",HttpStatus.OK);
		  }
		  else
			  return new ResponseEntity<>("Please Logout and Login again to continue our Services.", HttpStatus.UNAUTHORIZED);
		} 
	  
	  
	 @PostMapping(value="/AddyourProduct")
	 public ResponseEntity<?> addyourProduct(HttpServletRequest request, @RequestParam("category") String category,
			 @RequestParam("subcategory") String subcategory, @RequestParam("size") String size, @RequestParam("name") String name,
			 @RequestParam("origprice") String origprice, @RequestParam("offerprice") String offerprice, @RequestParam("longdescription") String longdescription,
			 @RequestParam("files") MultipartFile[] uploadfile){
		
		  final String requestTokenHeader = request.getHeader("Authorization");
		  DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		  if(daouser!=null)
		  {
				String imagename=addProductService.addproductimage(uploadfile);
				if(!imagename.isEmpty())
				{
					addProductService.addproduct(String.valueOf(daouser.getId()),imagename,  category, subcategory, size, name, origprice, offerprice, longdescription);
					return new ResponseEntity<>("product added successfully.", HttpStatus.OK);	 	
				}
				else
				    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  else
			  return new ResponseEntity<>("Please Logout and Login again to continue our Services.", HttpStatus.UNAUTHORIZED);
		
	} 
	 
	 @PostMapping(value="/edityourProduct/{id}")
	 public ResponseEntity<?> edityourProduct(HttpServletRequest request, @RequestParam("category") String category,
			 @RequestParam("subcategory") String subcategory, @RequestParam("size") String size, @RequestParam("name") String name,
			 @RequestParam("origprice") String origprice, @RequestParam("offerprice") String offerprice, @RequestParam("longdescription") String longdescription,
			 @RequestParam("files") MultipartFile[] uploadfile,@PathVariable int id){
		
		  final String requestTokenHeader = request.getHeader("Authorization");
		  DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		  String imagename=null;
		  if(daouser!=null)
		  {     if(uploadfile.length!=0)
				 imagename=addProductService.addproductimage(uploadfile);
		  
				boolean result=    addProductService.editproduct(String.valueOf(daouser.getId()),imagename,  category, subcategory, size, name, origprice, offerprice, longdescription,id);
				if(result)
				   return new ResponseEntity<>("product updated successfully.", HttpStatus.OK);	 	
				
				else
				    return new ResponseEntity<>("something went wrong.",HttpStatus.BAD_REQUEST);
		  }
		  else
			  return new ResponseEntity<>("Please Logout and Login again to continue our Services.", HttpStatus.UNAUTHORIZED);
		
	} 
	
}
