package com.agave.model.tcp;

public class SingleData {
	private String name = "";
	private String encode = "";
	private String datatype = "";
	private String text = "";
	private String value = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String toString(){
		String re = String.format("{name:'%s',encode:'%s',datatype:'%s',text:'%s',value:'%s'}", this.name,this.encode,this.getDatatype(),this.text,this.value);
		return re;
	}
}
