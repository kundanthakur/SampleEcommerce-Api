package com.omni.curis.repository;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.omni.curis.entity.Product;



@Repository
public interface ProductRepo extends CrudRepository<Product, Integer>{
	Product findById(int id);
	
	Product findByIdAndIsactive(int id,boolean isactive);
	
	ArrayList<Product> findByTag(String tag,Pageable pageable);
	
	ArrayList<Product> findByIsactive(boolean isactive,Pageable pageable);
	
	List<Product> findByNameIgnoreCaseContainingAndIsactive(String text,boolean isactive,Pageable pageable);
	
	List<Product> findByCategoryAndIsactive(String category,boolean isactive,Pageable pageable);
	
	List<Product> findByCategoryAndSubcategoryAndIsactive(String category,String subcategory,boolean isactive,Pageable pageable);
	
}
