package com.agave.model.gui;

import java.util.ArrayList;
import java.util.Iterator;

import com.agave.common.ALogger;
import com.agave.common.APar;
import com.agave.common.AgaveData;
import com.agave.core.Agave;
import com.agave.core.IGuiModel;
import com.agave.core.IRemoteData;
import com.agave.core.IReturnData;
import com.agave.data.DataCell;
import com.agave.robot.RemoteServer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GuiModelBean implements IGuiModel{
	
	private ALogger loger = ALogger.getLogger(this.getClass());
	
	private String name;
	
	private String type;
	
	private ArrayList<GuiElement> elements;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<GuiElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<GuiElement> elements) {
		this.elements = elements;
	}
	
	public String toString(){
		String el=this.elements.toString();
		String re = String.format("{name:'%s',type:'%s',elements:%s}", this.name,this.type,el);
		return re;
	}

	private IRemoteData parRemoteData(){
		
		//传递到远端
 		IRemoteData adata=new AgaveData();
 		//设置必要的调用信息
 		adata.addCaseinfo(APar.type, this.type);
 		adata.addCaseinfo(APar.a_class, this.getClass().getName());
 		return adata;
 		
	}
	private JSONObject toModelInfo(){
		String elements="";
		String tojson;
		
		if(null!=this.elements){
			elements=this.elements.toString();
			 tojson="{"
					+"name:'"+this.getName()+"',"
					+"type:'"+this.getType()+"',"
					+"elements:"+elements
					+"}";
		}else{
			 tojson="{"
					+"name:'"+this.getName()+"',"
					+"type:'"+this.getType()+"'"
					+"}";
		}
		JSONObject returndata=JSONObject.fromObject(tojson);
		loger.info("获取json对象:"+tojson);
		return returndata;
		
	}
	//获取模型及数据表，根据相关关键字进行处理。发送到远端。注意补充关键字
	//获取通用配置文件的rul
	public IReturnData open() {
		//String c_url=AProperties.getInstance().getCommonValue("URL");
		//去掉url为空的情况
		return this.open(null);
	}

	
	public IReturnData open(String url) {
		
				
		JSONObject redata=this.toModelInfo();//获取模型基本信息
		redata.accumulate("url", url);
		redata.accumulate("keyword", "open");
		IRemoteData rd=this.parRemoteData();
		rd.setRemotedata(redata); 
		loger.info("发送前："+rd.toString());
		IReturnData return_data= RemoteServer.getInstance().request(rd);//发送到远端服务器，获取返回值
		return return_data;
		
	}

	
	public IReturnData close() {
		JSONObject redata=this.toModelInfo();//获取模型基本信息
		redata.accumulate("keyword", "close");
		IRemoteData rd=this.parRemoteData();
		rd.setRemotedata(redata); 
		loger.info("发送前："+rd.toString());
		IReturnData return_data= RemoteServer.getInstance().request(rd);//发送到远端服务器，获取返回值
		return return_data;
	}

	
	public IReturnData type(String dataid) {
		JSONObject redata = this.toModelInfo();// 获取模型基本信息
		redata.accumulate("keyword", "type");
		ArrayList<DataCell> data;
		// if(null==uuid){
		String uuid = null;
		data = (ArrayList<DataCell>) Agave.getInstance().ExcelData(dataid, uuid);
		// }else{
		// data=(ArrayList<DataCell>)
		// Agave.getInstance().ExcelData(dataid,uuid);
		// //替换变量的临时写法
		// }
		if(null==data){
			loger.error("没有读取到数据表："+this.name+":"+this.type);
		}
		loger.debug("取得excel数据：" + data);
		loger.debug("redata" + redata);

		JSONArray newArray = new JSONArray();
		JSONObject newJson = new JSONObject();

		String inputValue = null;
		if (null != data){
			try {
				Iterator it = redata.keys();
				while (it.hasNext()) {
					String key = (String) it.next();
					String value = redata.getString(key);
					if ("elements".equals(key)) {
						JSONArray array = redata.getJSONArray(key);
						
						for (int i = 0; i < array.size(); i++) {
							JSONObject jsonobject = array.getJSONObject(i);
							for (DataCell dc : data) {
								if (null != jsonobject && jsonobject.containsValue(dc.getTitleName())) {
									//替换值
									inputValue = dc.getDataValue().replaceAll("X{5}",String.format("%4s", String.valueOf((int)(Math.random()*10000))).replaceAll("\\s", "0"));//随机数
									inputValue = inputValue.replace("${path}", System.getProperty("user.dir"));//路径
									//加入json串
									jsonobject.accumulate("inputValue", inputValue);
									loger.debug("加入json的excel数据：" + dc.getTitleName());
									
									//替换定位器的问号“？”
									//ArrayList locs=jsonobject.getString("locations");
									
									JSONArray locs=JSONArray.fromObject(jsonobject.getString("locations"));
									JSONArray newlocs = new JSONArray();
									for(int j=0;j<locs.size();j++){
										
										JSONObject loc=locs.getJSONObject(j);
										String loc_text=loc.getString("text");
										if(null!=loc_text&&loc_text.contains("?")){
											
											String newloc=loc_text.replace("?", inputValue);
											//System.out.println("loc_text:"+loc_text);
											loc.remove("text");
											loc.accumulate("text", newloc);
										}
										
										newlocs.add(loc);
									}
									jsonobject.remove("locations");
									jsonobject.accumulate("locations", newlocs);
									//如果inputValue为空，则该element不加入json串
									if (!"".equals(jsonobject.get("inputValue")) && jsonobject.get("inputValue") != null){
										newArray.add(jsonobject);
									}
								}
							}	
						}
					}
					newJson.accumulate(key, value);
				}
				//对输出进行排序
				JSONArray decArry = new JSONArray();
				for(DataCell dc : data){
					for(int k=0;k<newArray.size();k++){
						
						JSONObject jsonobject = newArray.getJSONObject(k);
						
						if(null != jsonobject && jsonobject.get("name").equals(dc.getTitleName())){
							decArry.add(jsonobject);
						}
						
					}
					
				}
					
				newJson.put("elements", decArry);
				
			} catch (Exception e) {
				loger.error("error:" + e.toString());
			}
			loger.debug("newJson:" + newJson);
		}
		//查询定位信息替换 问号
				
		
		
		IRemoteData rd = this.parRemoteData();
		rd.setRemotedata(newJson);
		loger.info("发送前：" + rd.toString());
		IReturnData return_data = RemoteServer.getInstance().request(rd);// 发送到远端服务器，获取返回值
		return return_data;

	}

	public IReturnData click(String dataid) {
		// TODO Auto-generated method stub
		return null;
	}

	public IReturnData doubleClick(String dataid) {
		// TODO Auto-generated method stub
		return null;
	}

	public IReturnData rightClick(String dataid) {
		// TODO Auto-generated method stub
		return null;
	}

	public IReturnData input(String dataid) {
		// TODO Auto-generated method stub
		return null;
	}

	public IReturnData get(String dataid) {
		// TODO Auto-generated method stub
		return null;
	}

}
