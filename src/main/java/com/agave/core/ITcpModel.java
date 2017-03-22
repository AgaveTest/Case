package com.agave.core;

public interface ITcpModel extends IModel{

	IReturnData send(String dataid);

	IReturnData send(String dataid, String uuid);

}
