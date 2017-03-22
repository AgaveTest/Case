package test.agave;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.agave.core.Agave;


public class GUITest {
	
	public static void main(String[] args) {
		  System.out.println("start");
		  System.out.println("sysout:"+System.getProperty("user.dir") + File.separator + "lib" + File.separator + "IEDriverServer.exe");
	}
//  @Test
  public void testGuiBean() {
	  
//	  System.setProperty("webdriver.firefox.bin", "/Applications/Firefox");
//	  String a=System.getProperty("webdriver.firefox.bin");
//	  System.out.println(a);
//	  WebDriver webDriver = new FirefoxDriver();
//	  webDriver.get("http://www.baidu.com");
	  //open的语法
	  Agave.getInstance().GuiModel(null).open("http://www.baidu.com");
	  
	 // Agave.getInstance().GuiModel("Login").open("http://www.baidu.com");
	  
	  
  }
	/**
	 * @see 测试数据准备
	 * @return
	 */
	@DataProvider(name = "data")
	public Object[][] data_provider(){
		
		Object[][] data = new Object[2][];
		String dataLine = null;
		for (int i = 0; i < data.length; i++){
			dataLine = "Login.L"+String.valueOf(i+1);
			data[i] =  new Object[]{dataLine};
		}
		return data;	
	}
	
  @Test(dataProvider = "data")
  public void testLogin(String dataLine) {
	  
//	  System.setProperty("webdriver.firefox.bin", "/Applications/Firefox");
//	  String a=System.getProperty("webdriver.firefox.bin");
//	  System.out.println(a);
//	  WebDriver webDriver = new FirefoxDriver();
//	  webDriver.get("http://www.baidu.com");
	  //open的语法

	  Agave.getInstance().GuiModel(null).open("http://192.168.210.59:8089/photon-wallet/");
	  Agave.getInstance().GuiModel("Login").type(dataLine);
//	  Agave.getInstance().GuiModel(null).close();
	 // Agave.getInstance().GuiModel("Login").open("http://www.baidu.com");
	  
	  
  }

  @AfterClass
  public void dataDel(){ 
//	  Agave.getInstance().GuiModel(null).close();  
  }
}
