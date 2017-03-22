package test.agave;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.agave.common.ALogger;

public class Tlog {
  @Test
  public void f() {
	  
	  Logger  log= Logger.getLogger(Tlog.class);
	 
	  log.debug("This is debug!");
	  log.info("This is info!");
	  System.out.println(log.isInfoEnabled());
	  System.out.println(log.isInfoEnabled());
	  System.out.println(log.isTraceEnabled());
	  Reporter.log("I'm log");
	
	  ALogger  logger= ALogger.getLogger(Tlog.class);
	  
	  logger.debug("this is alog debug");
	  logger.info("this is Alog info");
	  logger.error("This is Alog error!"+logger.getClass());
	  Assert.assertEquals(logger.getClass(), "class com.agave.common.ALogger");
	  
	  
  }
  
}
