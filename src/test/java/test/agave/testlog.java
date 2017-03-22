package test.agave;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.poifs.property.PropertyConstants;

import com.agave.common.A4log;
import com.agave.core.AgaveCommonData;

public class testlog {

	static Logger loger = Logger.getLogger (testlog.class);
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure(AgaveCommonData.LOG_CONFIG_FILE);
		loger.info("abc");
		
		
		
	}

}
