package com.agave.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.agave.core.AgaveCommonData;


public class AProperties {
	private static AProperties ap=null;
	
	public static AProperties getInstance() {
		if (ap == null) {
			synchronized (AProperties.class) {
				if (ap == null) {
					ap = new AProperties();
				}
			}
		}
		return ap;
	}
	public String getCommonValue(String key){
		
		Properties p=this.getproperty();
		String result=p.getProperty(key);
		
		return result;
	
	}
	private Properties getproperty(){
		
		return this.getproperty(AgaveCommonData.PRO_FILEPATH);
		
	}
	public Properties getOtherProperty(String filepath){
		
		return this.getproperty(filepath);
		
	}
	private Properties getproperty(String filepath){
		Properties p=new Properties();
		InputStream in = null;
		try {
			 in=new BufferedInputStream(new FileInputStream(filepath));
			p.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return p;
	}

}
