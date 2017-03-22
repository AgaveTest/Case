package com.agave.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.agave.model.rest.RestModelBean;

public class ReadDataSource {
	
	public HashMap<String,DataSBean> getDataSource(){
		
		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档
		HashMap<String,DataSBean> datasource=new HashMap<String,DataSBean>();
		try {
			Document doc = reader.read(new File("./src/test/resources/prodouct/config/DataSource.xml"));
			Element root = doc.getRootElement();
			List<Node> nodelist = root.selectNodes("//datasource");

			for(Node node:nodelist){
				DataSBean dsb=new DataSBean();
				Element e=(Element)node;
				dsb.setName(e.attributeValue("name"));
				Element type = (Element) node.selectSingleNode("//type");	
				dsb.setType(type.getText());
				Element classname = (Element) node.selectSingleNode("//parserclass");	
				dsb.setClassname(classname.getText());
				datasource.put(dsb.getName(), dsb);
			}
			return datasource;
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return null;
		
	}

}
