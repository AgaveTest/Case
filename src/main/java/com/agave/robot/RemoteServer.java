package com.agave.robot;

import com.agave.core.IRemoteData;
import com.agave.core.IRemoteServer;
import com.agave.core.IReturnData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

import java.util.HashMap;

import com.agave.common.ALogger;
import com.agave.common.ReadCommdata;
import com.agave.core.Agave;

public class RemoteServer implements IRemoteServer{

	private ALogger loger = ALogger.getLogger(this.getClass());
	private static RemoteServer instance=null;
	private String sid=null;
	private String tarsurl=this.gitUrl();
	private HashMap<String, String> head=new HashMap<String, String>();
	  public static RemoteServer getInstance(){
	        if(instance==null){
	            synchronized(Agave.class){
	                if(instance==null){
	                    instance=new RemoteServer();
	                }
	            }
	        }
	        return instance;
	    }
	  private RemoteServer(){
		  		  this.run();
		  		  this.getSid();
	  };
	public Boolean run() {
		// 后续真实的远端调用再补充
		head.put("Content-type", "application/json");
		this.getSid();
		return true;
	}

	public IReturnData request(IRemoteData sd) {

		try {
			Thread.sleep(this.gitSpeed());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//根据模型类型调用不同的robot发送指令
		if(null==this.sid){
			this.getSid();
		}
		ReturnData rd=new ReturnData();
				//封装发送数据
		JSONObject redata=JSONObject.fromObject(sd.toString());
		redata.accumulate("sid", this.sid);  
//		redata.accumulate("type", "case");  
		
		HttpUnit client=new HttpUnit();
	    String result=client.doJSONPost(this.tarsurl+"/talk", redata.toString(), this.head);
	    loger.debug("receive message(tars /talk)"+result);
	    try{
	    JSONObject res=JSONObject.fromObject(result);
	    if(res.get("result").equals("success")){
	    	rd.setIssuccess(true);
	    	rd.setSummary(result);
	    }else{
	    	rd.setIssuccess(false);
	    	rd.setFailmassage(result);
	    }}catch(Exception e){
	    		loger.error(e.getMessage());
	    		loger.error(e.getStackTrace());
	    }
		return rd;
	}
	public void getSid(){
		HttpUnit client=new HttpUnit();
		JsonArray arry = new JsonArray();  
        JsonObject j = new JsonObject();  
        j.addProperty("name", System.getProperty("user.name"));  
        j.addProperty("type", "case");  
        j.addProperty("sid", "");
        arry.add(j); 
        String result=client.doJSONPost(this.tarsurl+"/hello", arry.toString(), head);
        loger.debug(" receive message(tars /hello):"+result);
        JSONObject redata=JSONObject.fromObject(result.toString());
        this.sid=redata.getString("sid");
	}
	private String gitUrl(){
		JSONObject comdata=ReadCommdata.getInstance().getCommondata();
		String re="";
		if(comdata.getJSONObject("TARS")!=null){
			JSONObject tars=comdata.getJSONObject("TARS");
			re="http://"+tars.getString("Tarsip")+":"+tars.getString("Tarsport");
		}else{
			//没有配置ip
		}
		loger.info("TRAS url is:"+re);
		return re;
	}
	
	private int gitSpeed(){
		JSONObject comdata=ReadCommdata.getInstance().getCommondata();
		int re=0;
		if(comdata.getJSONObject("WaitTime")!=null){
			JSONObject WaitTime=comdata.getJSONObject("WaitTime");
			re=Integer.parseInt(WaitTime.getString("SendTars_space"));
		}else{
			//没有配置ip
		}
		loger.info("Server speed is:"+re);
		return re;
	}

}
