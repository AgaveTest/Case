package com.agave.core;

import com.agave.common.APar;

import net.sf.json.JSONObject;

public interface IRemoteData {

	public abstract JSONObject getCaseinfo();

	public abstract void setCaseinfo(JSONObject caseinfo);

	public abstract JSONObject getCommondata();

	public abstract void setCommondata(JSONObject commondata);

	public abstract JSONObject getRemotedata();

	public abstract void setRemotedata(JSONObject remotedata);

	public abstract JSONObject getOtherdata();

	public abstract void setOtherdata(JSONObject otherdata);

	public abstract void addCaseinfo(APar headkey, String value);

	public abstract String toString();
	
	public JSONObject getModelinfo() ;

	public void setModelinfo(JSONObject modelinfo);


}