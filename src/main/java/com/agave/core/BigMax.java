package com.agave.core;

import java.util.List;
import java.util.UUID;

import com.agave.common.ALogger;
import com.agave.common.APar;
import com.agave.common.AgaveData;
import com.agave.data.DataCell;
import com.agave.model.gui.GuiModelBean;
import com.agave.model.gui.ReadGUIXML;
import com.agave.model.rest.ReadRestXML;
import com.agave.model.rest.RestModelBean;
import com.agave.model.tcp.ReadTCPXML;
import com.agave.model.tcp.TcpModelBean;

public class BigMax implements IBigMax {
	
	private ALogger loger = ALogger.getLogger(this.getClass());
	private Class cls;
	private AgaveData  agave_data=new AgaveData();
	
	public static IBigMax Step(Class cls) {
		return new BigMax(cls);  //new 太难看了换个写法
	}

	private BigMax(Class name) {
		// 初始化agave 默认模型及数据表
		Agave.getInstance().parseModelXml(null, null);
		Agave.getInstance().parseData(null, null);
		// this.datasource=new ReadDataSource().getDataSource();
		this.setCasename(name);
	}

	
	
	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#RestModel(java.lang.String)
	 */
	public IRestModel RestModel(String modelname) {
		agave_data.addCaseinfo(APar.name,this.cls.getName());
		agave_data.addCaseinfo(APar.a_class, this.getClass().getName());
		agave_data.addCaseinfo(APar.type,"rest");
		
		ReadRestXML rrx = new ReadRestXML(this.agave_data);
		// 模型太多这里会出bug，系统变慢	
		//需要重构一种模式，每一次测试步骤都会读取一次xml系统执行会比较慢。目前先考虑实现
		for (RestModelBean rmb : rrx.readmodel()) {

			if (rmb.getName().equals(modelname)) {
				

				return rmb;
			}
		}
		return null;// 应该抛没有找到模型异常，暂时不加
	}

	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#ExcelData(java.lang.String)
	 */
	public List<DataCell> ExcelData(String dataid) {
		if (null != Agave.getInstance().getM_datatable()&& 
				!Agave.getInstance().getM_datatable().isEmpty() && 
				Agave.getInstance().getM_datatable().containsKey("datatable")) {
			return Agave.getInstance().getM_datatable().get("datatable").getDataNameTolis(dataid);
		} else {
			return null; // 没有获取到数据表解析类
		}
	}

	
	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#setValue(java.lang.String, java.lang.String)
	 */
	public String setValue(String Dataid, String Value) {

		TempValueBean tvb = new TempValueBean(Dataid, Value);
		String uuid = UUID.randomUUID().toString();
		Agave.getInstance().getM_tvalue().put(uuid, tvb);
		loger.info(String.format("设置数据表变量（dataid：%s,Value: %s ),uuid: %s", Dataid, Value, uuid));
		return uuid;
	}


	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#getValue(java.lang.String)
	 */
	public TempValueBean getValue(String uuid) {
		return Agave.getInstance().getM_tvalue().get(uuid);
	}

	
	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#GuiModel(java.lang.String)
	 */
	public IGuiModel GuiModel(String modelname) {
		// TODO Auto-generated method stub
		// 模型文件地址：
		if (null == Agave.getInstance().getGuimodellist()) {
			loger.info("读取GUI模型数据");
			ReadGUIXML rgx = new ReadGUIXML();// 模型太多这里会出bug，系统变慢
			Agave.getInstance().setGuimodellist(rgx.readmodel());
		}
		
		for (GuiModelBean gmb : Agave.getInstance().getGuimodellist()) {
			
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

	
	/* (non-Javadoc)
	 * @see com.agave.core.IBigMax#TcpModel(java.lang.String)
	 */
	public ITcpModel TcpModel(String modelname) {
		
		// 模型文件地址：
		if (null == Agave.getInstance().getTcpmodellist()) {
			loger.info("读取TCP模型数据");
			ReadTCPXML rtx = new ReadTCPXML();// 模型太多这里会出bug，系统变慢
			Agave.getInstance().setTcpmodellist(rtx.readmodel());
		}
		
		for (TcpModelBean tmb : Agave.getInstance().getTcpmodellist()) {
			
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

	public Class getCasename() {
		return this.cls;
	}

	public void setCasename(Class casename) {
		this.cls = casename;
	}

}
