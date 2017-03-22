package test.agave;

import org.testng.annotations.Test;

import com.agave.core.BigMax;
import com.agave.core.IReturnData;

public class AngularTest {
	
	
  @Test
  public void AngularTest() {
	  
	  IReturnData rd1= BigMax.Step(this.getClass()).GuiModel(null).open("http://127.0.0.1:3023");
	  //System.out.println("rd1:========"+rd1.getSummary());
	  IReturnData rd2=BigMax.Step(this.getClass()).GuiModel("Angular_sort").type("Angular_sort.L001");
	  //System.out.println("rd2:========"+rd2.getSummary());
      
	  //IReturnData rd3=BigMax.Step(this.getClass()).GuiModel(null).close();
	   //System.out.println("rd3:========="+rd3.getSummary());
	  //Agave.getInstance().GuiModel("Login").type("L1");
	  
	  
	  
  }
}
