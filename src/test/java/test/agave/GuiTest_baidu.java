package test.agave;

import org.testng.annotations.Test;

import com.agave.core.Agave;
import com.agave.core.BigMax;
import com.agave.core.IReturnData;

public class GuiTest_baidu {
  @Test
  public void f() {
	 
	  //BigMax.Step(this.getClass()).GuiModel(null).open("http://192.168.12.45/angular_filter_sort.html");
	  
	  IReturnData rd1= BigMax.Step(this.getClass()).GuiModel(null).open();
	  System.out.println("rd1:========"+rd1.getSummary());
	  IReturnData rd2=BigMax.Step(this.getClass()).GuiModel("Baidu").type("Baidu.L001");
	  System.out.println("rd2:========"+rd2.getSummary());

	  IReturnData rd3=BigMax.Step(this.getClass()).GuiModel(null).close();
	  System.out.println("rd3:========="+rd3.getSummary());
	  //Agave.getInstance().GuiModel("Login").type("L1");
 
  }
  
}
