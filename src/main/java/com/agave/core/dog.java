package com.agave.core;

import java.util.HashMap;
import java.util.List;

import com.agave.model.gui.GuiModelBean;
import com.agave.model.rest.RestModelBean;
import com.agave.model.tcp.TcpModelBean;

public class dog {

	public HashMap<String, IModel> getM_model() {
		return m_model;
	}
	public void setM_model(HashMap<String, IModel> m_model) {
		this.m_model = m_model;
	}
	public HashMap<String, IDataTable> getM_datatable() {
		return m_datatable;
	}
	public void setM_datatable(HashMap<String, IDataTable> m_datatable) {
		this.m_datatable = m_datatable;
	}
	public HashMap<String, TempValueBean> getM_tvalue() {
		return m_tvalue;
	}
	public void setM_tvalue(HashMap<String, TempValueBean> m_tvalue) {
		this.m_tvalue = m_tvalue;
	}
	public List<RestModelBean> getRestmodellist() {
		return restmodellist;
	}
	public void setRestmodellist(List<RestModelBean> restmodellist) {
		this.restmodellist = restmodellist;
	}
	public List<GuiModelBean> getGuimodellist() {
		return guimodellist;
	}
	public void setGuimodellist(List<GuiModelBean> guimodellist) {
		this.guimodellist = guimodellist;
	}
	public List<TcpModelBean> getTcpmodellist() {
		return tcpmodellist;
	}
	public void setTcpmodellist(List<TcpModelBean> tcpmodellist) {
		this.tcpmodellist = tcpmodellist;
	}
	private HashMap<String, IModel> m_model = new HashMap<String, IModel>();
	private HashMap<String, IDataTable> m_datatable = new HashMap<String, IDataTable>();
	private HashMap<String, TempValueBean> m_tvalue = new HashMap<String, TempValueBean>();

	private List<RestModelBean> restmodellist;
	private List<GuiModelBean> guimodellist;
	private List<TcpModelBean> tcpmodellist;
}
