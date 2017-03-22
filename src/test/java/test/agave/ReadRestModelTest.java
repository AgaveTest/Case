package test.agave;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;



import com.agave.core.IReturnData;
import com.agave.model.rest.ReadRestXML;
import com.agave.model.rest.RestModelBean;
import com.agave.robot.ReturnData;

public class ReadRestModelTest {
//  @Test
//  public void resdxmlTest() {
//	  
//	  ReadRestXML rrx=new ReadRestXML();
//	  
//	  ArrayList<RestModel> rds=(ArrayList<RestModel>) rrx.readmodel("./src/test/resources/prodouct/model/RestModel.xml");
//	 
//	  if(rds.size()==0){
//		  Assert.fail();//没有获取到对象
//	  }
//	  for(RestModel r:rds){
////		  if(r.getName().equals("dafult")){
////			  Assert.assertEquals(r.getName(), "dafult");
////			  Assert.assertEquals(r.getHttpmodel(), "GET");
////			  Assert.assertEquals(r.getType(), "rest");
////			  Assert.assertEquals(r.getSdata().getHead(), "httphead=$xxx;aaa=$aaa");
////			  Assert.assertEquals(r.getSdata().getBody(), "{abc:$abcd}");
////			  Assert.assertEquals(r.getReturndatas().get(0).getName(), "displayName");
////			  Assert.assertEquals(r.getReturndatas().get(0).getValue(), "//displayName");
////			  Assert.assertEquals(r.getReturndatas().get(0).getType(), "string");
////		  }
//	  }
//  }
  @Test
  public void sendTest() {
	  ReadRestXML rrx=new ReadRestXML();
	  ArrayList<RestModelBean> rds=(ArrayList<RestModelBean>) rrx.readmodel("./src/test/resources/prodouct/model/RestModel.xml");
		 
	  for(RestModelBean r:rds){
		IReturnData rd=  r.send("datasheet.L001");
		  System.out.println(rd.toString());
	  }
  }
  //测试为空时是否能够读取
  @Test
  public void sendJenkins() {
	  ReadRestXML rrx=new ReadRestXML();
	  ArrayList<RestModelBean> rds=(ArrayList<RestModelBean>) rrx.readmodel("./src/test/resources/prodouct/model/RestModelTest.xml");
	 Boolean result=false; 
	  for(RestModelBean r:rds){
		if(r.getName().equals("Jenkins-getjob1")){
			
			Assert.assertEquals(r.getSdata().getHead().toString(), "head1");   
			result=true;
		}
		if(r.getName().equals("Jenkins-getjob2")){
			
			Assert.assertEquals(r.getSdata().getHead().toString(), "head2");   
			result=true;
		}
		if(r.getName().equals("Jenkins-getjob3")){
			Assert.assertEquals(r.getSdata().getHead().toString(), "");  
			result=true;
		}
		
	  }
	  if(!result){
		 Assert.assertFalse(true);
	  }
  }
  
}
