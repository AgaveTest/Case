package com.agave.common;

import com.agave.core.IRemoteData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AgaveData  implements IRemoteData{
	
	//name:数据的名字 data 数据表类型
	
	//private String head = "{name=\"none\",agave_type=\"none\",class=\"none\",}"; 
	private String head;
	
	//具体数据使用set方法更方便，此为默认值。设置为空。
	private String body="{}";
	
	//j_head与j_body应该何并
	
	
	private JSONObject caseinfo=JSONObject.fromObject("{}");;  //存储运行过程中的测试用例相关信息
	
	private JSONObject modelinfo=JSONObject.fromObject("{}");  //存储运行过程中的测试用例相关信息
	private JSONObject commondata=ReadCommdata.getInstance().getCommondata(); //存储全局变量
	
	private JSONObject remotedata=JSONObject.fromObject("{}"); //存储远程数据
	
	private JSONObject otherdata=JSONObject.fromObject("{}"); //扩展数据
	
	public AgaveData(){
		this.head=String.format("{%s=\"none\",%s=\"none\",%s=\"none\"}", APar.name.toString(),
				APar.type.toString(),APar.a_class.toString());
		this.setCaseinfo(JSONObject.fromObject(head));
		//this.c_data=rcd.getInstance().getJSON();
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#getCaseinfo()
	 */
	public JSONObject getCaseinfo() {
		return caseinfo;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#setCaseinfo(net.sf.json.JSONObject)
	 */
	public void setCaseinfo(JSONObject caseinfo) {
		this.caseinfo = caseinfo;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#getCommondata()
	 */
	public JSONObject getCommondata() {
		return commondata;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#setCommondata(net.sf.json.JSONObject)
	 */
	public void setCommondata(JSONObject commondata) {
		this.commondata = commondata;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#getRemotedata()
	 */
	public JSONObject getRemotedata() {
		return remotedata;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#setRemotedata(net.sf.json.JSONObject)
	 */
	public void setRemotedata(JSONObject remotedata) {
		this.remotedata = remotedata;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#getOtherdata()
	 */
	public JSONObject getOtherdata() {
		return otherdata;
	}

	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#setOtherdata(net.sf.json.JSONObject)
	 */
	public void setOtherdata(JSONObject otherdata) {
		this.otherdata = otherdata;
	}

	
	
	/*public JSONObject getBody(){
		return this.j_body;
	}
	public JSONObject getHead(){
		return this.j_head;
	}
	public void setHeadValue(APar headkey,String value){
		
			this.c_head.put(headkey.toString(), value);
	}
	
	public void setBody(JSONObject jbody){
		this.j_body=jbody;
	}
	public void setHead(JSONObject jhead){
		this.j_head=jhead;
	}
	public JSONObject getCData(){
		
		return this.c_data;
	}

	@Override
	public String getHeadValue(APar headkey) {
		return this.c_head.getString(headkey.toString());
	}*/
	
	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#addCaseinfo(com.agave.common.APar, java.lang.String)
	 */
	public void addCaseinfo(APar headkey,String value){
		
		this.caseinfo.put(headkey.toString(), value);
		
}
	
	/* (non-Javadoc)
	 * @see com.agave.common.IRemoteData#toString()
	 */
	@Override
	public String toString(){
		return "{Caseinfo:"+this.getCaseinfo().toString()+","
				+"modelinfo:"+this.getModelinfo().toString()+","
				+ "Commondata:"+this.getCommondata().toString()+","
				+"Remotedata:"+this.getRemotedata().toString()+","
				+"Otherdata:"+this.getOtherdata().toString()+"}";
	}

	public JSONObject getModelinfo() {
		return modelinfo;
	}

	public void setModelinfo(JSONObject modelinfo) {
		this.modelinfo = modelinfo;
	}
}
