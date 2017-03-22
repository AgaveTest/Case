package com.agave.model.tcp;

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

public class ReadTCPXML {

	private Logger log = Logger.getLogger(ReadTCPXML.class);
	
	public List<TcpModelBean> readmodel(){
		return this.readmodel(AgaveCommonData.tcpxmlpath);
	}
	public List<TcpModelBean> readmodel(String filepath) {
	
		ArrayList<TcpModelBean> rdata = new ArrayList<TcpModelBean>();
		SAXReader reader = new SAXReader(); // User.hbm.xml表示你要解析的xml文档
		
		try {
			Document doc = reader.read(new File(filepath));
			Element root = doc.getRootElement();
			List<Node> nodelist = root.selectNodes("//model[@type='tcp']");

			for (Node e : nodelist) {

				TcpModelBean rm = this.getTcpModel(e);
				rdata.add(rm);
			}
			
			List<Node> nodelist2 = root.selectNodes("//model[@type='udp']");
			
			for (Node e : nodelist2) {

				TcpModelBean rm = this.getTcpModel(e);
				rdata.add(rm);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return rdata;
	}
	
	private TcpModelBean getTcpModel(Node node) {
		
		TcpModelBean rmodel = new TcpModelBean();
		Element e = (Element) node;
		
		if (null != e.attributeValue("type")) {
			rmodel.setType(e.attributeValue("type"));
		}
		if (null != e.attributeValue("name")) {
			rmodel.setName(e.attributeValue("name"));
		}
		
		//获取commands
		List<Element> commandList = node.selectNodes("//model[@name='"+rmodel.getName()+"']/commands/command");	
		if (!commandList.isEmpty()) {
			for (Element redata : commandList) {

				SingleData rd = new SingleData();
				rd.setName(redata.attributeValue("name"));
				rd.setEncode(redata.attributeValue("encode"));
				rd.setText(redata.getText());
				rmodel.getCommands().add(rd);
			}
		}
		//获取datas
		List<Element> dataList = node.selectNodes("//model[@name='"+rmodel.getName()+"']/datas/data");	
		if (!dataList.isEmpty()) {
			for (Element redata : dataList) {

				SingleData rd = new SingleData();
				rd.setName(redata.attributeValue("name"));
				rd.setEncode(redata.attributeValue("encode"));
				rd.setDatatype(redata.attributeValue("datatype")==null?"null":redata.attributeValue("datatype"));
				rd.setText(redata.getText());
				rmodel.getDatas().add(rd);
			}
		}
		
		//return data
		List<Element> redatalist = node.selectNodes("//model[@name='"+rmodel.getName()+"']/returndatas/returndata");
		if (!redatalist.isEmpty()) {
			for (Element redata : redatalist) {

				TcpReturnData rd = new TcpReturnData();
				rd.setName(redata.attributeValue("name"));
				rd.setType(redata.attributeValue("type"));
				rd.setValue(redata.getText());
				rmodel.getReturndatas().add(rd);
			}
		}
		
		return rmodel;
	}
}
