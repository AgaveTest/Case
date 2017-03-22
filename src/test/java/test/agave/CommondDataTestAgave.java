package test.agave;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.common.rcd;

public class CommondDataTestAgave {
  @Test
  public void readDataTest() {
	  
	  Assert.assertEquals(rcd.getInstance().get("dafult_url"), "http://www.baidu.com");
	  Assert.assertEquals(rcd.getInstance().get("dafult_waittime"), "1000");
	  Assert.assertEquals(rcd.getInstance().get("db_rul"), "192.168.0.1");
	  Assert.assertEquals(rcd.getInstance().get("db_password"), "qwerty");
	  
  }
  @Test
  public void readJSONTest(){
	  
	  Assert.assertEquals(rcd.getInstance().getJSON().get("dafult_url"), "http://www.baidu.com");
	  Assert.assertEquals(rcd.getInstance().getJSON().get("dafult_waittime"), "1000");
	  Assert.assertEquals(rcd.getInstance().getJSON().get("db_rul"), "192.168.0.1");
	  Assert.assertEquals(rcd.getInstance().getJSON().get("db_password"), "qwerty");
	  
	  
  }
}
