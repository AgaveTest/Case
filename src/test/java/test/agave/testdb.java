package test.agave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.agave.common.DB;

public class testdb {
  @Test
  public void testupdate() {
	  String url = "jdbc:mysql://192.168.210.122:3306/test?"
              + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
	  HashMap<String,String> db=new HashMap<String,String>();
	  db.put("url", url);
	  
	  String sql = "insert into student(NO,name) values('202093','陈九思')";
	  
	  DB db1=new DB();
	  
	  System.out.println(db1.executeUpdate(db, sql));
  }

@Test
public void testSelect() {
	  String url = "jdbc:mysql://192.168.210.122:3306/test?"
            + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
	  HashMap<String,String> db=new HashMap<String,String>();
	  db.put("url", url);
	  
	  String sql = "select * from student";
	  
	  DB db1=new DB();
	  ArrayList<Map<String,String>> resultdata=db1.executeQuery(db, sql);
	  System.out.println(resultdata.toString());
}


	@Test
	public void testSqlServerSelect(){
		
		String url="jdbc:sqlserver://192.168.10.40:1433;DatabaseName=CJP_GMDB";
		String user="kc2015";
		String password="kc2015";
		String sql = "select t.* from T_Base_Card t where id = 188";
		
		HashMap<String,String> db=new HashMap<String,String>();
		db.put("url", url);
		db.put("user", user);
		db.put("password", password);
		
		DB db1=new DB("sqlserver");
		ArrayList<Map<String,String>> resultdata=db1.executeQuery(db, sql);
		System.out.println(resultdata.toString());
	}
	
	@Test
	public void testSqlServerUpdate(){
		
		String url="jdbc:sqlserver://192.168.10.40:1433;DatabaseName=CJP_GMDB";
		String user="kc2015";
		String password="kc2015";
		String sql = "delete from T_Base_Card where id = 188";
		
		HashMap<String,String> db=new HashMap<String,String>();
		db.put("url", url);
		db.put("user", user);
		db.put("password", password);
		
		DB db1=new DB();
		  
		System.out.println("sqlserver:"+db1.executeUpdate(db, sql));
	}
}
