package test.agave;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.common.AgaveData;
import com.agave.common.ADType;
import com.agave.common.APar;
import com.agave.core.IRemoteData;

public class AgaveDataTest {
  @Test
  public void dataenum() {
	  Assert.assertEquals(ADType.DATATABLE.toString(), "DATATABLE");
	  Assert.assertEquals(ADType.MODEL.toString(), "MODEL");
  }
  
  @Test
  public void jsontest() {
	  
	  IRemoteData ad=new AgaveData();
	  //agave 默认值
//	  Assert.assertEquals(ad.getHead().get("name"), "none");
//	  //设置name变量名
//	  ad.setHeadValue(APar.name, "jsontest");
//	  //检查名字是否替换
//	  Assert.assertEquals(ad.getHead().get("name").toString(), "jsontest");
  }
	  
  @Test
  public void getclass() {
	  
	 
  }
	  
	  
}
