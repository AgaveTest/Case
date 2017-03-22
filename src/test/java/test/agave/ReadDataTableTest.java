package test.agave;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.common.APar;
import com.agave.common.ReadExcelFile;
import com.agave.core.IRemoteData;
import com.agave.data.DataCell;
import com.agave.data.ReadDataTable;

public class ReadDataTableTest {
  @Test
  public void ReadTest() {
	  
	  
	  ReadExcelFile ref=new ReadExcelFile();
	  Workbook wb=ref.getCaseFiles("./src/test/resources/prodouct/data/ReadDataTableTest.xlsx");
	  ReadDataTable rdt=new ReadDataTable();
	  ArrayList<DataCell> datalist= (ArrayList<DataCell>) rdt.getDataName(wb, "datasheet.L001");
//	  测试点：1、测试读取出2一行数据 2、标题和内容均正确 3、数值以string型读出
//	  System.out.println(datalist.toString());
	  Assert.assertEquals(datalist.get(0).getTitleName(), "title1");
	  Assert.assertEquals(datalist.get(1).getDataValue(), "123");
  }
  @Test
  public void ReadDTTest() {
	  
//	  测试点：测试读取某一行某一个数据 输入格式  datasheet.title.line
	  ReadExcelFile ref=new ReadExcelFile();
	  Workbook wb=ref.getCaseFiles("./src/test/resources/prodouct/data/ReadDataTableTest.xlsx");
	  ReadDataTable rdt=new ReadDataTable();
	  ArrayList<DataCell> datalist= (ArrayList<DataCell>) rdt.getDataName(wb, "datasheet.L001.title1");

	  Assert.assertEquals(datalist.get(0).getTitleName(), "title1");
	  Assert.assertEquals(datalist.get(0).getDataValue(), "hello");
  }
  
  @Test
  public void ReadJSONTest() {
	  
//	  测试点：测试读取某一行某一个数据 输入格式  datasheet.title.line 以json形式读
	  ReadDataTable rdt=new ReadDataTable();
//	  IRemoteData adata= rdt.getDataName("./src/test/resources/prodouct/data/ReadDataTableTest.xlsx", "datasheet.L001.title1");
//
//	  Assert.assertEquals(adata.getHead().getString(APar.name.toString()), "ExcelData");
//	  Assert.assertEquals(adata.getBody().getString("title1"), "hello");
  }
  
}
