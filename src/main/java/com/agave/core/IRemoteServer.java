package com.agave.core;

public interface IRemoteServer {
	
	public Boolean run();//启动服务
	public IReturnData request(IRemoteData rdata);
	

}
