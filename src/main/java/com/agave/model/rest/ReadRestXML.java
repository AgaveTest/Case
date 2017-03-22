package com.agave.model.rest;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.testng.log4testng.Logger;

import com.agave.common.AgaveData;
import com.agave.core.AgaveCommonData;
import com.agave.core.IRemoteData;


public class ReadRestXML {

	private Logger log = Logger.getLogger(ReadRestXML.class);
	private IRemoteData adata=new AgaveData();
	public ReadRestXML(){
		
	}
	public ReadRestXML(IRemoteData data){
		this.adata=data;
	}
	public List<RestModelBean> readmodel(){
		return this.readmodel(AgaveCommonData.restxmlpath);
	}
	public List<RestModelBean> readmodel(String filepath) {

		ArrayList<RestModelBean> rdata = new ArrayList<RestModelBean>();
		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档

		try {
			Document doc = reader.read(new File(filepath));
			Element root = doc.getRootElement();
			List<Node> grolbdatalist = root.selectNodes("//GlobalData/data");
			HashMap<String,String> gbmap=new HashMap<String,String>();
			for(Node gb:grolbdatalist){
				Element e = (Element) gb;
				if (null != e.attributeValue("name")) {
					gbmap.put(e.attributeValue("name"),e.getText());
				}
				
			}
			List<Node> nodelist = root.selectNodes("//model[@type='rest']");
			
			for (Node e : nodelist) {

				RestModelBean rm = this.getRestModel(e,gbmap);
				rdata.add(rm);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return rdata;
	}

	private RestModelBean getRestModel(Node node,HashMap gb) {
		RestModelBean rmodel = new RestModelBean(this.adata);
		rmodel.setGlobaldata(gb);
		Element e = (Element) node;
		if (null != e.attributeValue("name")) {
			rmodel.setName(e.attributeValue("name"));
		}

		if (null != e.attributeValue("url")) {
			rmodel.setUrl(e.attributeValue("url"));
		}

		if (null != e.attributeValue("httpmodel")) {
			rmodel.setHttpmodel(e.attributeValue("httpmodel"));
		}
		//测试驱动开发，发现如下问题
		if (null != e.attributeValue("type")) {
			rmodel.setType(e.attributeValue("type"));
		}
		
//		System.out.println(node.asXML());
		//Element head = (Element) node.selectSingleNode("/model/send/head");
//		Element head = null;
		Element head = (Element) node.selectSingleNode("//model[@name='"+rmodel.getName()+"']/send/head");
		SendData sd = new SendData();
		if (null != head) {
			sd.setHead(head.getText());
		}
		Element body = (Element) node.selectSingleNode("//model[@name='"+rmodel.getName()+"']/send/parm");
		if (null != body) {
			sd.setBody(body.getText());
		}
		rmodel.setSdata(sd);
		List<Element> redatalist = node.selectNodes("//model[@name='"+rmodel.getName()+"']/returndatas/returndata");
		if (!redatalist.isEmpty()) {
			for (Element redata : redatalist) {

				RestReturnData rd = new RestReturnData();
				rd.setName(redata.attributeValue("name"));
				rd.setType(redata.attributeValue("type"));
				rd.setValue(redata.getText());
				rmodel.getReturndatas().add(rd);
			}
		}
		
		return rmodel;
	}

}
