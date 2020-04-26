package com.omni.curis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.omni.curis.entity.PlacedOrder;
import com.omni.curis.entity.Product;
import com.omni.curis.entity.ProductOrder;
import com.omni.curis.repository.ProductOrderRepo;
import com.omni.curis.repository.ProductRepo;




@Service
public class ProductService {
	
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ProductOrderRepo productOrderRepo;
	
	public List<String> yourorders(String emailId)
	{
		 List<String> list=new ArrayList<>();
		 try
		 {
			 List<PlacedOrder> orderlist=productOrderRepo.findByEmailId(emailId);
			 
			 for(PlacedOrder order:orderlist)
			 {
				 Product product=productRepo.findById(order.getProductId());
				 String info="name of the product : "+product.getName()+"Delivery Address:"+order.getAddress() +" Delivery Status :";
				 if(order.isDelivered())
					 info+="Delivered";
				 else
					 info+="On the way.";
				 list.add(info);
			 }
			 
		 }catch (Exception e) {
			 list.add("can't find anything with this emailId");
		}
		return list;
	}
	
	  public List<String> findandplaceorder(ProductOrder productOrder)
	  {
		  List<String> list=new ArrayList<>();
		  try
		  {
			  for(int id:productOrder.getListofitems())
			  {
				  Product product=productRepo.findByIdAndIsactive(id, true);
				  if(product!=null)
				  {
					  PlacedOrder order=new PlacedOrder();
					  order.setAddress(productOrder.getAddress());
					  order.setDelivered(false);
					  order.setEmailId(productOrder.getEmailId());
					  order.setMobileno(productOrder.getMobileno());
					  order.setName(productOrder.getName());
					  order.setProductId(id);
					  productOrderRepo.save(order);
					  list.add("Product with productId :"+id+" successfully placed.");
				  }
				  else
				  {
					  list.add("Product with productId :"+id+" not placed due to unavailability please try after some hours.");
				  }
			  }
		  }catch (Exception e) {
			  list.add("Failed to process your order");
			  
		}
		  return list;
		  
	  }
	
	   public List<Product> getyourproct(int page)
		   {
			return productRepo.findByIsactive(true,PageRequest.of(page, 8,Sort.by("entrydate").descending()));
		   }
	  
	   public Product getsingleyourproct(int id)
		   {
			   return productRepo.findById(id);
		   }
	   
	   public List<Product> getproductbyname(String text,int pageno)
	   {
		   return productRepo.findByNameIgnoreCaseContainingAndIsactive(text, true, PageRequest.of(pageno, 8,Sort.by("entrydate").descending()));
	   }
	
	   public List<Product> getProductByCategoryAndSubcategory(String category,String subcategory,int pageno)
	   {
		   return productRepo.findByCategoryAndSubcategoryAndIsactive(category, subcategory, true,PageRequest.of(pageno, 8,Sort.by("entrydate").descending()));
	   }
	   
	   public List<Product> getProductByCategory(String category,int pageno)
	   {
		   return productRepo.findByCategoryAndIsactive(category, true, PageRequest.of(pageno, 8,Sort.by("entrydate").descending()));
	   }
	   

}
