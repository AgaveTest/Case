package test.agave;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.core.DataSBean;
import com.agave.core.ReadDataSource;

public class ReadDataSourceTest {
  @Test
  public void readTest() {
	  
	  ReadDataSource rds=new ReadDataSource();
	  
	  HashMap<String,DataSBean> data= rds.getDataSource();
	  Assert.assertEquals(data.get("DataTable").getName(), "DataTable");
  }
}
