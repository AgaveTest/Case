package com.agave.model.rest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.agave.common.ALogger;
import com.agave.core.IKeyWord;
import com.agave.core.IRemoteData;
import com.agave.core.IRestModel;
import com.agave.core.IReturnData;
import com.agave.core.Agave;
import com.agave.data.DataCell;
import com.agave.robot.RemoteServer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RestModelBean implements IRestModel,IKeyWord{
	private String name="";
	private String type="";
	private String url;
	private String httpmodel;
	private SendData sdata;
	private IRemoteData adata;
	private ArrayList<RestReturnData> returndatas=new ArrayList<RestReturnData>();
	private HashMap<String,String> globaldata=new HashMap<String,String>();
	
	 private ALogger logger=ALogger.getLogger(this.getClass());
	
	public RestModelBean(IRemoteData data){
		this.adata=data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpmodel() {
		return httpmodel;
	}

	public void setHttpmodel(String httpmodel) {
		this.httpmodel = httpmodel;
	}

	public SendData getSdata() {
		return sdata;
	}

	public void setSdata(SendData sdata) {
		this.sdata = sdata;
	}

	public ArrayList<RestReturnData> getReturndatas() {
		return returndatas;
	}

	public void setReturndatas(ArrayList<RestReturnData> returndatas) {
		this.returndatas = returndatas;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public  IReturnData send(String dataid){
		String str = null;
		return this.send(dataid,str);
	}
	
	public  IReturnData send(String dataid,String uuid){
		//替换变量的临时写法
		ArrayList<DataCell> data=(ArrayList<DataCell>) Agave.getInstance().ExcelData(dataid,uuid);
		
		return this.send(dataid, data);
	}

	public IReturnData send(String dataid, Map<String,String> activeData){
		
		//替换变量的临时写法
		ArrayList<DataCell> data=(ArrayList<DataCell>) Agave.getInstance().ExcelData(dataid,activeData);
		
		return this.send(dataid, data);
	}
	public IReturnData send(String dataid,ArrayList<DataCell> data) {
	
		//很多bug在下面-by chenjisui 
		//处理模型数据
		JSONObject head =null;
		JSONObject body =null;
		JSONObject remotedata=null;
		
		try{
		if(null!=this.getSdata().getHead()&&!"".equals(this.getSdata().getHead())){
			 head = JSONObject.fromObject(this.getSdata().getHead());
		}}catch(Exception e){
			logger.error("head转换json异常，后续修改日志"+this.getClass().getName());
		}
		try{
		if(null!=this.getSdata().getBody()&&!"".equals(this.getSdata().getBody())){
			 body = JSONObject.fromObject(this.getSdata().getBody());
		}
		}catch(Exception e){
			logger.error("body转换json异常，后续修改日志:"+this.getClass().getName());
	}

		
	    //替换head and body 中的变量
		if(null!=data){//后续修改添加没有数据的提示
		for(DataCell dc:data){
			if(null!=head&&head.containsKey(dc.getTitleName())){
				head.remove(dc.getTitleName());
				head.accumulate(dc.getTitleName(), dc.getDataValue());
			}
			if(null!=body&&body.containsKey(dc.getTitleName())){
				body.remove(dc.getTitleName());
				String value = dc.getDataValue().replaceAll("X{4}",String.format("%4s", String.valueOf((int)(Math.random()*10000))).replaceAll("\\s", "0"));
//				datas.get(j).setValue(value);
				body.accumulate(dc.getTitleName(), value);
			}
		}
		}
		
		//模型数据替换全局变量。（暂时未实现）
		//传递到远端
 		
 		//设置必要的调用信息
 		remotedata=JSONObject.fromObject("{head:"+head+",body:"+body+"}");
 		
//		adata.setBody(body);
//		adata.setHead(head);
 		adata.setRemotedata(remotedata);
		adata.setModelinfo(this.toModelInfo());
		logger.info(adata.toString());
		
 		IReturnData rd= RemoteServer.getInstance().request(adata);
		//处理返回值，临时放在这里后面再考虑
 		if(null!=rd.getAllValue()&&!rd.getAllValue().equals("")){
 			JSONObject rdreturn=JSONObject.fromObject(rd.getAllValue());
 			for(RestReturnData rdata:this.returndatas){
 				
 				String[] key=rdata.getValue().split("@");
 				JSONObject rvalue=rdreturn;
 				if (null==key||key.length==0) continue;
 				try{
 				for(int i=0;i<key.length-1;i++){
 					rvalue=rvalue.getJSONObject(key[i]);//遍历
 				}
 				if("string".equals(rdata.getType().toLowerCase())){
 					rd.getMapValue().put(rdata.getName(), rvalue.getString(key[key.length-1]));
 				}
 				if("list".equals(rdata.getType().toLowerCase())){
 					JSONArray rlist=rvalue.getJSONArray(key[key.length-1]);
 					for(int i=0;i<rlist.size();i++){
 						rd.getMapValue().put(rdata.getName(), rlist.get(i));
 					}
 					
 				}}catch(Exception e){
 					
 					logger.error("无法转换返回值请使用summy自行判断：（注代码后续修改）");

 				}
 			}
		}
		return rd;
		
	}

	private JSONObject toModelInfo(){
		
		String rs="{"
				+"name:'"+this.getName()+"',"
				+"type:'"+this.getType()+"',"
				+"url:'"+this.getUrl()+"',";
		if(this.getGlobaldata().containsKey("host")){
			rs=rs+"host:'"+this.getGlobaldata().get("host")+"',";
		}
		if(this.getGlobaldata().containsKey("post")){
			rs=rs+"post:'"+this.getGlobaldata().get("post")+"',";
		}
		rs=rs+"httpmodel:'"+this.getHttpmodel()+"'}";
		JSONObject returndata=JSONObject.fromObject(rs);
		return returndata;
	}
	public HashMap<String,String> getGlobaldata() {
		return globaldata;
	}
	public void setGlobaldata(HashMap<String,String> globaldata) {
		this.globaldata = globaldata;
	}
}
