package com.agave.core;

public interface IGuiModel extends IModel{
	
	public IReturnData open();
	public IReturnData open(String url);
	public IReturnData close();
	public IReturnData type(String dataid);
	public IReturnData click(String dataid);
//	public IReturnData drog(String dataid);
	public IReturnData doubleClick(String dataid);
	public IReturnData rightClick(String dataid);
//	public IReturnData select(String dataid);
	public IReturnData input(String dataid);
	public IReturnData get(String dataid);

}
