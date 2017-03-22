package com.agave.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agave.data.DataCell;
import com.agave.model.gui.GuiModelBean;
import com.agave.model.rest.RestModelBean;
import com.agave.model.tcp.TcpModelBean;

public interface Iagave {
	
	IModel Model(String modelname);

	IRestModel RestModel(String modelname);
	IGuiModel GuiModel(String modelname);
	ITcpModel TcpModel(String modelname);

	//	//根据模型类型和路径初始化，默认报名下model.xml
	void parseModelXml(String typee, String path);

	void parseData(String type, String path);

	IDataTable Data(String data);

	List<DataCell> ExcelData(String dataid,String uuid);
	
	List<DataCell> ExcelData(String dataid, Map<String,String> activeData);

	String getFirstMaker();
	
	//临时方法满足接口自动化测试需求，后续讨论对于测试框架的整体模型
	
	public String setValue(String Title,String Value); //
	public TempValueBean getValue(String uuid);
	
	public HashMap<String, IModel> getM_model() ;
		
	public void setM_model(HashMap<String, IModel> m_model) ;
	
	public HashMap<String, IDataTable> getM_datatable() ;
	
	public void setM_datatable(HashMap<String, IDataTable> m_datatable) ;
	
	public HashMap<String, TempValueBean> getM_tvalue() ;
		
	public void setM_tvalue(HashMap<String, TempValueBean> m_tvalue) ;
		
	public List<RestModelBean> getRestmodellist() ;
	
	public void setRestmodellist(List<RestModelBean> restmodellist) ;
		
	public List<GuiModelBean> getGuimodellist() ;
		
	public void setGuimodellist(List<GuiModelBean> guimodellist) ;
	
	public List<TcpModelBean> getTcpmodellist() ;
	public void setTcpmodellist(List<TcpModelBean> tcpmodellist);
	
	

}
