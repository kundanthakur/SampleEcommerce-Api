package com.omni.curis.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omni.curis.entity.DAOUser;




@Repository
public interface UserRepo extends CrudRepository<DAOUser, Integer>{
	
	DAOUser findByEmail(String email); 
	

}
