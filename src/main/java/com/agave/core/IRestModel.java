package com.agave.core;

import java.util.Map;

public interface IRestModel extends IModel{
	
	public IReturnData send(String dataid);

	public IReturnData send(String dataid,String uuid);
	
	public IReturnData send(String dataid, Map<String,String> activeData);

	//公开取得url方法
	public String getUrl();
}
