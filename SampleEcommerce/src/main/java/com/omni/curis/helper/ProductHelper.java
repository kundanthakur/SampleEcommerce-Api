package com.omni.curis.helper;

import java.util.Random;
import java.util.UUID;

public class ProductHelper {
          
	
	
	 public static int generateUniqueId() {      
	        UUID idOne = UUID.randomUUID();
	        String str=""+idOne;        
	        int uid=str.hashCode();
	        String filterStr=""+uid;
	        str=filterStr.replaceAll("-", "");
	        return Integer.parseInt(str);
	    }
}
