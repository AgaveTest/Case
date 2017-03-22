package com.agave.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agave.common.ALogger;
import com.agave.data.DataCell;
import com.agave.data.ReadDataTable;
import com.agave.model.gui.GuiModelBean;
import com.agave.model.gui.ReadGUIXML;
import com.agave.model.rest.ReadRestXML;
import com.agave.model.rest.RestModelBean;
import com.agave.model.tcp.ReadTCPXML;
import com.agave.model.tcp.TcpModelBean;

import java.util.UUID;

/**
 * 目前只支持固定默认目录模型与数据表，多数据表支持后续补充。
 * 
 * 2016-12-14 为支持分布式用例执行，用例执行类改成BigMax 后续陆续禁止使用Agave,Agave 用于单例执行 by jiusi.chen
 * 
 * @author jiusi.chen
 *
 */

public class Agave implements Iagave {
	private ALogger loger = ALogger.getLogger(this.getClass());
	private HashMap<String, IModel> m_model = new HashMap<String, IModel>();
	private HashMap<String, IDataTable> m_datatable = new HashMap<String, IDataTable>();
	private HashMap<String, TempValueBean> m_tvalue = new HashMap<String, TempValueBean>();

	private List<RestModelBean> restmodellist;
	private List<GuiModelBean> guimodellist;
	private List<TcpModelBean> tcpmodellist;
	// private HashMap<String,DataSBean> datasource;
	private String fistmaker;
	private static Agave instance = null;
	

	public static Iagave getInstance(Class cls) {
		if (instance == null) {
			synchronized (Agave.class) {
				if (instance == null) {
					instance = new Agave();
					instance.fistmaker = cls.getName();
				}
			}
		}
		return instance;
	}

	public static Iagave getInstance() {
		if (instance == null) {
			synchronized (Agave.class) {
				if (instance == null) {
					instance = new Agave();
					instance.fistmaker = instance.getClass().getName();
				}
			}
		}
		return instance;
	}

	private Agave() {
		// 初始化agave 默认模型及数据表
		this.parseModelXml(null, null);
		this.parseData(null, null);
		// this.datasource=new ReadDataSource().getDataSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agave.core.Iagave#Model(java.lang.String)
	 */
	public IModel Model(String modelname) {
		if (this.m_model.containsKey(modelname)) {
			return this.m_model.get(modelname);
		} else {
			return null;// 应抛出没有找到模型异常
		}
	}

	
	public IRestModel RestModel(String modelname) {

		// 模型文件地址：
		if (null == this.restmodellist) {
			loger.info("读取Rest模型数据");
			ReadRestXML rrx = new ReadRestXML();// 模型太多这里会出bug，系统变慢

			this.restmodellist = rrx.readmodel();
		}
		for (RestModelBean rmb : this.restmodellist) {

			if (rmb.getName().equals(modelname)) {
				return rmb;
			}
		}
		return null;// 应该抛没有找到模型异常，暂时不加
	}

	// //根据模型类型和路径初始化，默认报名下model.xml

	public void parseModelXml(String typee, String path) {
		if (typee == null && path == null) {

		}
		// if(null!=this.datasource){
		//
		// for(Iterator<String>
		// it=this.datasource.keySet().iterator();it.hasNext();){
		//
		// String key=it.next();
		// DataSBean dstemp=this.datasource.get(key);
		// if(dstemp.getType().equals("model")){
		// try {
		// Class c =Class.forName(dstemp.getClassname());
		// IModel model=(IModel) c.newInstance();
		// this.m_model.put(dstemp.getName(), model);
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InstantiationException e) {
		// // TODO 如果不是model 接口类报错
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// // TODO 如果不是model 接口类报错
		// e.printStackTrace();
		// }
		// }
		//
		// }
		// }
		//
	}

	
	public void parseData(String type, String path) {
		// DefaultDataTable dt=new DefaultDataTable();
		// this.m_datatable.put("defaultdata", dt);
		ReadDataTable dt = new ReadDataTable();
		this.m_datatable.put("datatable", dt);
	}

	
	public IDataTable Data(String data) {

		if (this.m_datatable.containsKey(data)) {
			return this.m_datatable.get(data);
		} else {
			return null;// 应抛出没有找到模型异常
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agave.core.Iagave#ExcelData(java.lang.String)
	 */

	public List<DataCell> ExcelData(String dataid) {
		if (null != this.m_datatable && !this.m_datatable.isEmpty() && this.m_datatable.containsKey("datatable")) {
			return this.m_datatable.get("datatable").getDataNameTolis(dataid);
		} else {
			return null; // 没有获取到数据表解析类
		}
	}

	
	public List<DataCell> ExcelData(String dataid, String uuid) {

		List<DataCell> ld = this.ExcelData(dataid);
		if (null == uuid) {
			return ld;
		}
		if (!this.m_tvalue.containsKey(uuid)) {
			loger.error("未查询到临时变量，uuid:" + uuid);
			return ld;
		} else {
			String titlename = this.getValue(uuid).getDataid();
			String Value = this.getValue(uuid).getValue();
			for (DataCell dc : ld) {
				if (dc.getTitleName().equals(titlename)) {
					dc.setDataValue(Value);// 变量替换
				}
			}
			return ld;
		}
	}
	
	
	public List<DataCell> ExcelData(String dataid, Map<String,String> activeData) {

		List<DataCell> ld = this.ExcelData(dataid);
		if (null == activeData) {
			return ld;
		} else {
			for (DataCell dc : ld) {
				if (activeData.containsKey(dc.getTitleName())){
					dc.setDataValue(activeData.get(dc.getTitleName()));// 变量替换
				}
			}
			return ld;
		}
	}

	
	public String getFirstMaker() {
		return this.fistmaker;
	}

	
	public String setValue(String Dataid, String Value) {

		TempValueBean tvb = new TempValueBean(Dataid, Value);
		String uuid = UUID.randomUUID().toString();
		this.m_tvalue.put(uuid, tvb);
		loger.info(String.format("设置数据表变量（dataid：%s,Value: %s ),uuid: %s", Dataid, Value, uuid));
		return uuid;
	}


	public TempValueBean getValue(String uuid) {
		return this.m_tvalue.get(uuid);
	}

	
	public IGuiModel GuiModel(String modelname) {
		// TODO Auto-generated method stub
		// 模型文件地址：
		if (null == this.guimodellist) {
			loger.info("读取GUI模型数据");
			ReadGUIXML rgx = new ReadGUIXML();// 模型太多这里会出bug，系统变慢
			this.guimodellist = rgx.readmodel();
		}
		
		for (GuiModelBean gmb : this.guimodellist) {
			
			if(null==modelname){
				if (gmb.getName().equals("noModel")) 
					return gmb;
			}else{
			if (gmb.getName().equals(modelname)) {
				return gmb;
			}
			}
		}
		return null;// 应该抛没有找到模型异常，暂时不加
	}

	
	public ITcpModel TcpModel(String modelname) {
		
		// 模型文件地址：
		if (null == this.tcpmodellist) {
			loger.info("读取TCP模型数据");
			ReadTCPXML rtx = new ReadTCPXML();// 模型太多这里会出bug，系统变慢
			this.tcpmodellist = rtx.readmodel();
		}
		
		for (TcpModelBean tmb : this.tcpmodellist) {
			
			if(null==modelname){
				if (tmb.getName().equals("noModel")) 
					return tmb;
			}else{
				if (tmb.getName().equals(modelname)) {
					return tmb;
				}
			}
		}
		return null;// 应该抛没有找到模型异常，暂时不加
	}
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

	}
