package test.agave;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.core.BigMax;
import com.agave.core.IReturnData;

public class BigMaxTest {
  @Test
  public void TestBigMax() {
	  int b=0;
	  for(int i=1;i<2;i++){
	  
	  IReturnData rd= BigMax.Step(this.getClass()).RestModel("Jenkins-getjob").send("");
	  System.out.println(i+"    "+rd);
	  b++;
	  Assert.assertEquals(i, b);;
	  }
	 
  }
//  @Test
//  public void TestBigPost() {
//	  
//	  for(int i=0;i<10;i++){
//		  IReturnData rd= BigMax.Step(this.getClass()).RestModel("hello_model").send("hello_model.L001");
//	  }
//	  
//	  
	  //System.out.println(rd);
	  
 // }
}
