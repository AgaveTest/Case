package com.agave.robot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.agave.core.IReturnData;

import net.sf.json.JSONObject;

public class ReturnData implements IReturnData {

	private HashMap<String, Object> mapvalue = new HashMap<String, Object>();
	private Object value="";
	private Boolean issuccess = false;
	private String summary = "";
	private String failmassage = "";
//	private String cookies = "";
	

	public ReturnData() {
	}
	
	public void setMapvalue(HashMap<String, String> mapvalue) {
		for(Iterator<String> it=mapvalue.keySet().iterator();it.hasNext();){
			
			String key=it.next();
			this.mapvalue.put(key, mapvalue.get(key));
		}
	}
	public void  init(String result){
		
		this.summary=result;
		try{
		JSONObject redata=JSONObject.fromObject(result);
		this.issuccess=Boolean.valueOf(redata.get("result").toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	public String toString(){
		
		return "{ReturnData:{issuccess:"+this.issuccess.toString()+","
		+"summary:"+this.summary+","
		+"failmassage:"+this.failmassage+","
		+"value:"+this.value.toString()+","
		+"mapvalue:"+this.mapvalue.toString()+","
		+"}}";
		
	}
	



	public void setValue(Object value) {
		this.value = value;
	}

	

	public void setIssuccess(Boolean issuccess) {
		this.issuccess = issuccess;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setFailmassage(String failmassage) {
		this.failmassage = failmassage;
	}

	public void putMapValue(String name, String value) {
		this.mapvalue.put(name, value);
	}

//	public void setCookies(String cookies){
//		this.cookies = cookies;
//	}
//	
//	public String getCookies(){
//		return this.cookies;
//	}
	public Map<String, Object> getMapValue() {
		return this.mapvalue;
	}

	public Object getAllValue() {
		return this.value;
	}

	public Boolean isSuccess() {
		return this.issuccess;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getFailMassage() {
		return this.failmassage;
	}

	

}
