package test.agave;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import com.agave.common.A4log;
import com.agave.common.ALogger;
import com.agave.core.AgaveCommonData;

public class A4logTest {
	//static Logger loger = Logger.getLogger (testlog.class);
  @Test
  public void A4test() {
	  
//	  A4log log=A4log.getLogger(A4logTest.class);
//	  
////	  log.info("abc");
//	  PropertyConfigurator.configure(AgaveCommonData.LOG_CONFIG_FILE);
////	  Logger loger = Logger.getLogger (testlog.class);
//	loger.info("abc");
////	  A4log loger2=A4log.getLogger(testlog.class);
////		loger2.info("adsf");
	  ALogger loger= ALogger.getLogger(testlog.class);
	  loger.info("hello world");
  }
}
