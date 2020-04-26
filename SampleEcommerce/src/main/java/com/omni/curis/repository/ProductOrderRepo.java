package com.omni.curis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omni.curis.entity.PlacedOrder;

@Repository
public interface ProductOrderRepo extends CrudRepository<PlacedOrder, Integer>{
	
	List<PlacedOrder> findByEmailId(String emailId);
	

}
