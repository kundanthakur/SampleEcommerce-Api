package com.omni.curis.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omni.curis.entity.DAOUser;
import com.omni.curis.repository.UserRepo;
import com.omni.curis.service.DAOUserService;

@Service
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userDao;

	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private DAOUserService dAOUserService;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
	
	public DAOUser getdaouserfromtoken(String token)
	{
		 DAOUser daouser=null;
		  String username=null;
		     try
		     {
		    	 username=jwtTokenUtil.getUsernameFromToken(token);
		     }
		     catch (Exception e) {
			}
		  
		  if(username!=null && !username.isEmpty())
		  daouser=dAOUserService.checkDAOUser(username);
		   return daouser;
	}
}