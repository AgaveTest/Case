package com.agave.model.gui;

import java.util.ArrayList;

public class GuiElement {
	
	
	private String name;
	private String driver;
	private String type;
	private String inputValue;
	private ArrayList<Location> locations;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInputValue(){
		return inputValue;
	}
	public void setInputValue(String inputValue){
		this.inputValue = inputValue;
	}
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	public String toString(){
		String ll=this.locations.toString();
		String re = String.format("{name:'%s',type:'%s',locations:%s}", this.name,this.type,ll);
		return re;
	}
}
