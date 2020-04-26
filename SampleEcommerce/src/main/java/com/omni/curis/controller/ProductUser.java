package com.omni.curis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.omni.curis.entity.DAOUser;
import com.omni.curis.helper.Result;
import com.omni.curis.security.JwtUserDetailsService;
import com.omni.curis.service.DAOUserService;






@RestController
public class ProductUser {
	
	@Autowired
	private DAOUserService userService;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	
	@GetMapping(value="/checkAuth")
	public ResponseEntity<?>checkAuth(HttpServletRequest request)
	{
		 final String requestTokenHeader = request.getHeader("Authorization");
		 DAOUser daouser=jwtUserDetailsService.getdaouserfromtoken(requestTokenHeader);
		 
		 if(daouser!=null)
				return new ResponseEntity<>(daouser.getName(),HttpStatus.ACCEPTED);
		 else
			    return new ResponseEntity<>("Please Login Again",HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(value="/loginuser/{email}/{password}")
	public  ResponseEntity<?> loginuser(@PathVariable String email,@PathVariable String password)
	{
		Result result=userService.checklogin(email, password);
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	}

	
	
	@PostMapping(value="/AddUser")
	 public ResponseEntity<?> addUser(@RequestBody DAOUser dAOUser)
	{
		Result result=new Result();
		DAOUser user=userService.checkDAOUser(dAOUser.getEmail());
		if(user!=null)
		{
			result.setStatus(300);
			result.setMessage("This Emailid is Already registered With us.");
		}
		else
		{
			DAOUser daouser=userService.addDAOUser(dAOUser);
			result.setEmail(dAOUser.getEmail());
			result.setStatus(200);
			result.setMessage("You have successfully created your account with us.");
		}
		return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
	 }
		
}
