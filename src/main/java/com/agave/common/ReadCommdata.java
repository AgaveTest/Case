package com.agave.common;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.agave.core.Agave;
import com.agave.core.AgaveCommonData;

import net.sf.json.JSONObject;
public class ReadCommdata {
	private JSONObject commondata=JSONObject.fromObject("{}");
	private static ReadCommdata instance=null;
	
	public static ReadCommdata getInstance(){
		if (instance == null) {
			synchronized (Agave.class) {
				if (instance == null) {
					instance = new ReadCommdata();
					instance.getCommdata(AgaveCommonData.RCDFILEPATCH);
				}
			}
		}
		return instance;

	}
	
	private void getCommdata(String filepath){
		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档
		try {
			Document doc = reader.read(new File(filepath));
			Element root = doc.getRootElement();
			List<Node> nodelist = root.selectNodes("//datas/suit");
			
			for (Node  node: nodelist) {
				Element e = (Element) node;
				JSONObject bs=JSONObject.fromObject("{}");
				String name="";
				String type="";
				if (null != e.attributeValue("name")) {
					name=e.attributeValue("name");
					//bs.accumulate("name", name);
				}//需要抛出异常如果没有name
				if (null != e.attributeValue("type")) {
					 type=e.attributeValue("type");
					bs.accumulate("type", type);
				}//需要抛出异常如果没有name
				List<Node> datalist = root.selectNodes("//datas/suit[@type='"+type+"']/data");
				for(Node data:datalist){
					Element edata = (Element) data;
					if (null != edata.attributeValue("name")) {
					bs.accumulate(edata.attributeValue("name"), edata.getText());
					}
				}
				this.commondata.accumulate(name, bs);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getCommondata() {
		return commondata;
	}

	public void setCommondata(JSONObject commondata) {
		this.commondata = commondata;
	}
}
