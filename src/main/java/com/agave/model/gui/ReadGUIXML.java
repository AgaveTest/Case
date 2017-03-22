package com.agave.model.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.testng.log4testng.Logger;

import com.agave.core.AgaveCommonData;

public class ReadGUIXML {

	private Logger log = Logger.getLogger(ReadGUIXML.class);

	public List<GuiModelBean> readmodel() {
		return this.readmodel(AgaveCommonData.guixmlpath);
	}

	public List<GuiModelBean> readmodel(String filepath) {

		ArrayList<GuiModelBean> rdata = new ArrayList<GuiModelBean>();
		rdata.add(this.getnullModel());
		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档

		try {
			Document doc = reader.read(new File(filepath));
			Element root = doc.getRootElement();
			List<Node> nodelist = root.selectNodes("//model[@type='gui']");

			for (Node e : nodelist) {
				GuiModelBean rm = this.getModel(e);
				rdata.add(rm);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return rdata;
	}

	private GuiElement getElements(Node node,String modelName) {

		GuiElement ge = new GuiElement();
		Element e = (Element) node;
		if (null != e.attributeValue("name")) {
			ge.setName(e.attributeValue("name"));
		}
		// 测试驱动开发，发现如下问题
		if (null != e.attributeValue("type")) {
			ge.setType(e.attributeValue("type"));
		}

		// System.out.println(node.asXML());
		// Element head = (Element) node.selectSingleNode("/model/send/head");
		// Element head = null;
//		List<Node> nodelist = node.selectNodes("//location");
		List<Node> nodelist = node.selectNodes("/models/model[@name = '"+modelName+"']/element[@name = '"+ge.getName()+"']/location");
		ArrayList<Location> le = new ArrayList<Location>();
		for (Node n : nodelist) {
			Location loc = this.getLocations(n);
			le.add(loc);
		}
		ge.setLocations(le);

		return ge;
	}

	private Location getLocations(Node node) {

		Location le = new Location();
		Element e = (Element) node;
		if (null != e.attributeValue("driver")) {
			le.setDriver(e.attributeValue("driver"));
		}
		if (null != e.attributeValue("type")) {
			le.setType(e.attributeValue("type"));
		}
		if (null != e.getText()) {
			
			le.setText(e.getText());

		}

		return le;
	}

	private GuiModelBean getModel(Node node) {

		GuiModelBean model = new GuiModelBean();
		Element e = (Element) node;
		if (null != e.attributeValue("name")) {
			model.setName(e.attributeValue("name"));
		}
		// 测试驱动开发，发现如下问题
		if (null != e.attributeValue("type")) {
			model.setType(e.attributeValue("type"));
		}

		// System.out.println(node.asXML());
		// Element head = (Element) node.selectSingleNode("/model/send/head");
		// Element head = null;
//		List<Node> nodelist = node.selectNodes("//element");
		List<Node> nodelist = node.selectNodes("/models/model[@name = '"+model.getName()+"']/element");
//		List<Node> nodelist = node.selectNodes("/models/model/element");
		if(null!=nodelist){
			ArrayList<GuiElement> guie = new ArrayList<GuiElement>();

			for (Node n : nodelist) {
				GuiElement ge = this.getElements(n,model.getName());
				guie.add(ge);
			}

			model.setElements(guie);
		}
		
		return model;
	}
	private GuiModelBean getnullModel(){
		
		GuiModelBean model = new GuiModelBean();
		model.setName("noModel");
		model.setType("gui");		
		return model;
		
	}
}
