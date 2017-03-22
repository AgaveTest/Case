package com.agave.model.tcp;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.agave.common.ALogger;
import com.agave.common.APar;
import com.agave.common.AgaveData;
import com.agave.core.Agave;
import com.agave.core.IRemoteData;
import com.agave.core.IReturnData;
import com.agave.core.ITcpModel;
import com.agave.data.DataCell;
import com.agave.robot.RemoteServer;

public class TcpModelBean implements ITcpModel{
	private ALogger logger=ALogger.getLogger(this.getClass());
	
	private String type = "";
	private String name = "";
	private ArrayList<SingleData> commands = new ArrayList<SingleData>();
	private ArrayList<SingleData> datas = new ArrayList<SingleData>();
	private ArrayList<TcpReturnData> returndatas=new ArrayList<TcpReturnData>();
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SingleData> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<SingleData> commands) {
		this.commands = commands;
	}

	public ArrayList<SingleData> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<SingleData> datas) {
		this.datas = datas;
	}

	public ArrayList<TcpReturnData> getReturndatas() {
		return returndatas;
	}

	public void setReturndatas(ArrayList<TcpReturnData> returndatas) {
		this.returndatas = returndatas;
	}

	public  IReturnData send(String dataid){
		
		return this.send(dataid,null);
	}

	public IReturnData send(String dataid,String uuid) {
			
		ArrayList<DataCell> dataCell;
		if(null==uuid){
			dataCell=(ArrayList<DataCell>) Agave.getInstance().ExcelData(dataid, uuid);
		}else{
			dataCell=(ArrayList<DataCell>) Agave.getInstance().ExcelData(dataid,uuid);
			//替换变量的临时写法
		}
		
		JSONArray newCommandsArray = new JSONArray();
		JSONArray newDatasArray = new JSONArray();
		JSONObject newJson = new JSONObject();
		
	    //替换 command 中的变量
		if(null!=commands){
			for(DataCell dc:dataCell){
				for (int i = 0; i < commands.size(); i++){
					if (commands.get(i).getName().equals(dc.getTitleName())){
						if (null != dc.getDataValue() && !"".equals(dc.getDataValue())){
							commands.get(i).setValue(dc.getDataValue());
							newCommandsArray.add(JSONObject.fromObject(commands.get(i).toString()));
						}
					}
				}
			}
		}
		newJson.put("commands", newCommandsArray);
	    //替换 data 中的变量
		if(null!=datas){//后续修改添加没有数据的提示
			for(DataCell dc:dataCell){
				for (int j = 0; j < datas.size(); j++){
					if (datas.get(j).getName().equals(dc.getTitleName())){
						if(null != dc.getDataValue() && !"".equals(dc.getDataValue())){
							String value = dc.getDataValue().replaceAll("X{4}",String.format("%4s", String.valueOf((int)(Math.random()*10000))).replaceAll("\\s", "0"));
							datas.get(j).setValue(value);
							newDatasArray.add(JSONObject.fromObject(datas.get(j).toString()));
						}
					}
				}
			}
		}
		newJson.put("datas", newDatasArray);
		//模型数据替换全局变量。（暂时未实现）
		//传递到远端
 		IRemoteData adata=new AgaveData();
 		//设置必要的调用信息
 		adata.addCaseinfo(APar.type, this.type);
 		adata.addCaseinfo(APar.a_class, this.getClass().getName());
 		
 		adata.setRemotedata(newJson);
		logger.info(adata.toString());
		//发送数据到远端
 		IReturnData rd= RemoteServer.getInstance().request(adata);
 		
		//处理返回值，临时放在这里后面再考虑
 		if(null!=rd.getAllValue()&&!rd.getAllValue().equals("")){
 			JSONObject rdreturn=JSONObject.fromObject(rd.getAllValue());
 			for(TcpReturnData rdata:this.returndatas){
 				
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
		String commandsStr="";
		String datasStr="";
		String tojson;
		
		if(null!=this.commands){
			commandsStr = this.commands.toString();
		}
		if(null!=this.datas){
			datasStr = this.datas.toString();
		}
		
		tojson="{"
				+"name:'"+this.getName()+"',"
				+"type:'"+this.getType()+"',"
				+"commands:"+commandsStr + ","
				+"datas:" + datasStr
				+"}";		
		
		logger.info("获取json对象:"+tojson);
		JSONObject returndata=JSONObject.fromObject(tojson);
		return returndata;
	}
}
