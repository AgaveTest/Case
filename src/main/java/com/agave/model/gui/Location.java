package com.agave.model.gui;

public class Location {
	
	private String type;
	private String text;
	private String driver;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String toString(){
		String re = String.format("{driver:'%s',type:'%s',text:'%s'}", this.driver,this.type,this.text);
		return re;
	}
}
