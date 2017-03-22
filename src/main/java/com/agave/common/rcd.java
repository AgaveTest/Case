package com.agave.common;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import net.sf.json.JSONObject;

public class rcd {

	private HashMap<String, String> commondata;
	private String defaultpath = "./src/test/resources/prodouct/config/CommonData.xml";
	private JSONObject jsonobject = JSONObject.fromObject("{}");
	private static rcd instance = null;

	public static rcd getInstance() {
		if (instance == null) {
			synchronized (rcd.class) {
				if (instance == null) {
					instance = new rcd();
				}
			}
		}
		return instance;
	}

	private rcd() {

		this.commondata = new HashMap<String, String>();
		this.readxml(this.defaultpath);
		this.makeJSON();
	}

	private void readxml(String filepath) {

		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档

		try {
			Document doc = reader.read(new File(filepath));
			Element root = doc.getRootElement();
			List<Node> nodelist = root.selectNodes("//data");
			for (Node e : nodelist) {

				Element edata = (Element) e;
				if (null != edata.attributeValue("name") && null != edata.getText()) {
					this.commondata.put(edata.attributeValue("name"), edata.getText());
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public HashMap getMapData() {
		return this.commondata;
	}

	public void put(String name, String value) {
		this.commondata.put(name, value);
	}

	public String get(String name) {
		return this.commondata.get(name);
	}

	public Boolean isContain(String name) {
		if (this.commondata.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}

	private void makeJSON() {
		for (Iterator<String> it = this.commondata.keySet().iterator(); it.hasNext();) {
			String str = it.next();
			jsonobject.accumulate(str, this.commondata.get(str));
		}

	}

	public JSONObject getJSON() {
		return jsonobject;

	}
}
