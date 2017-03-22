package test.agave;

import java.util.List;

import org.testng.annotations.Test;

import com.agave.model.gui.GuiModelBean;
import com.agave.model.gui.ReadGUIXML;

public class ReadGuiXmlTest {
	
  @Test
  public void readxmltest() {
	  
	  
	  ReadGUIXML rgx=new ReadGUIXML();
	  
	 List<GuiModelBean> beans=rgx.readmodel();
	 for(GuiModelBean gmb:beans){
		 
		 System.out.println(gmb.toString());
	 }
	  
	  
	  
  }
}
