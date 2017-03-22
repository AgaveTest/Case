package com.agave.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.agave.core.AgaveCommonData;

//import test.agave.testlog;




public class A4log {
	
	static Logger loger ;
	
	A4log(Class pClass){
		PropertyConfigurator.configure(AgaveCommonData.LOG_CONFIG_FILE);
		loger= Logger.getLogger (pClass);
	}
	 public void info(Object message) {
		 loger.info(message);
		  
	  }

}
