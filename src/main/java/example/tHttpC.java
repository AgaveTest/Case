package example;

import java.util.HashMap;

import com.agave.robot.HttpUnit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class tHttpC {
	
	
	
	

	//test connect tars
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, String> head=new HashMap<String, String>();
		head.put("Content-type", "application/json");
		
		HttpUnit c=new HttpUnit();
		
		 JsonArray arry = new JsonArray();  
	        JsonObject j = new JsonObject();  
	        j.addProperty("name", "java-001");  
	        j.addProperty("type", "case");  
	        arry.add(j); 
	     c.doJSONPost("http://127.0.0.1:7988/hello", arry.toString(), head);
		
	}

}
