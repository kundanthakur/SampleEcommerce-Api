package com.omni.curis.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.omni.curis.entity.DAOUser;
import com.omni.curis.helper.PasswordHelper;
import com.omni.curis.helper.ProductHelper;
import com.omni.curis.helper.Result;
import com.omni.curis.repository.UserRepo;
import com.omni.curis.security.JwtTokenUtil;




@Service
public class DAOUserService {
	
	@Autowired
	private UserRepo DAOUserRepo;
	
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	
	public Result checklogin(String email,String password)
	{

		Result result=new Result();
		try
		{
			DAOUser user=DAOUserRepo.findByEmail(email);
			if(user.getEmail().equals(email) && password.equals(PasswordHelper.passwordDecode(user.getPassword())))
			{
				final UserDetails userDetails =new org.springframework.security.core.userdetails.User(email, password,new ArrayList<>());
                    String token=jwtTokenUtil.generateToken(userDetails);
					result.setStatus(200);
					result.setEmail(user.getEmail());
					result.setMessage("you logged-in successfully.");
					result.setJwstoken(token);
             
			}
			else
			{
				result.setStatus(400);
				result.setMessage("check your password.");

			}
		}
		catch (Exception e) {
			result.setStatus(500);
			result.setMessage("something went wrong.");
		}

		return result;
		
	
	}
	public DAOUser checkDAOUser(String email)
	{
		
		try
		{
			DAOUser dAOUser=DAOUserRepo.findByEmail(email);
			return dAOUser;
		}
		catch (Exception e) {
		
			return null;
		}
		
	}
		
     public DAOUser addDAOUser(DAOUser dAOUser)
	{
		try
		{
			
			dAOUser.setPassword(PasswordHelper.passwordEncode(dAOUser.getPassword()));
			DAOUserRepo.save(dAOUser);
			return dAOUser;
		}
		catch (Exception e) {
			return null;	
		}
		
	}

}
