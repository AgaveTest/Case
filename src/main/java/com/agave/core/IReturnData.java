package com.agave.core;

import java.util.Map;

public interface IReturnData {
	
	public Map<String,Object> getMapValue();
	public Object getAllValue();
	public Boolean isSuccess();
	public String getSummary();
//	public String getCookies();
	public String getFailMassage();
	public String toString();
	
}
