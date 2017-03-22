package com.agave.core;

public class TempValueBean {
	private String dataid;
	private String value;
	
	public TempValueBean(String dataid,String value){
		this.dataid=dataid;
		this.value=value;
	}
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
