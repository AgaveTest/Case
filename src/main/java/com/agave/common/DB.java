package com.agave.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB {
	
	ALogger log=ALogger.getLogger(DB.class);
	private String driverName = null;
	
	public DB(){
		driverName = "com.mysql.jdbc.Driver";
	}
	public DB(String type){
		//支持sqlserver add by 崔剑平 2016-06-30
		if ("mysql".equals(type.toLowerCase())){
			driverName = "com.mysql.jdbc.Driver";
		}else if("sqlserver".equals(type.toLowerCase())){
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		}else{
			driverName = "com.mysql.jdbc.Driver";
		}	
	}
	
	public boolean executeUpdate(Map<String,String> DBSource,String sql){
		Connection conn = null;
		 Statement stmt=null;
		 String url = null;
		 String user = null;
		 String password = null;
		if(DBSource.containsKey("url")){
		   url = DBSource.get("url");
		}else{
			log.error("DB 没有提供url（注意：大小写！）");
		}
		
		if (DBSource.containsKey("user") && DBSource.containsKey("password")){
			user = DBSource.get("user");
			password = DBSource.get("password");
		}
		  //String url 
		  try {
	            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
	            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
	            Class.forName(driverName);// 动态加载mysql驱动
	            // or:
	            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
	            // or：
	            // new com.mysql.jdbc.Driver();
	 
	            log.info("成功加载数据库驱动程序");
	            // 一个Connection代表一个数据库连接
	            if(null!=url){
	            	if (null != user && null != password){
	            		conn = DriverManager.getConnection(url,user,password);
	            	}else{
	            		conn = DriverManager.getConnection(url);
	            	}           	
	            }else{
	            	log.error("没有获取到url");
	            	return false;
	            }
	            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	           stmt = conn.createStatement();
	            int result = ((java.sql.Statement) stmt).executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	            if (result != -1) {
	            		return true;
	            	}else{
	            		return false;
	            	}
	            }catch (Exception e) {
	            	log.error(e.getMessage());
	            	return false;
	            }finally {
		        	
		            try {
		            	stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
		  
		  
	}
	
	public ArrayList<Map<String,String>> executeQuery(Map<String,String> DBSource,String sql){
		
		ArrayList<Map<String,String>> resultdata=new ArrayList<Map<String,String>> ();
		Connection conn = null;
		ResultSet rs =null;
		 Statement stmt=null;
		 String url = null;
		 String user = null;
		 String password = null;
		if(DBSource.containsKey("url")){
		   url = DBSource.get("url");
		}else{
			log.error("DB 没有提供url（注意：大小写！）");
		}
		if (DBSource.containsKey("user") && DBSource.containsKey("password")){
			user = DBSource.get("user");
			password = DBSource.get("password");
		}
		  //String url 
		  try {
	            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
	            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
	            Class.forName(driverName);// 动态加载mysql驱动
	            // or:
	            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
	            // or：
	            // new com.mysql.jdbc.Driver();
	 
	            log.info("成功加载数据驱动程序");
	            // 一个Connection代表一个数据库连接
	            if(null!=url){
	            	if (null != user && null != password){
	            		conn = DriverManager.getConnection(url,user,password);
	            	}else{
	            		conn = DriverManager.getConnection(url);
	            	}
	            }else{
	            	log.error("没有获取到url");
	            	return null;
	            }
	             stmt = conn.createStatement();
	             rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
	           
	            if(rs!=null){
	            	
	            	ResultSetMetaData rsmd = rs.getMetaData();  
	                int columnCount = rsmd.getColumnCount();  
	                // 输出列名  
	                ArrayList<String> columnname=new ArrayList<String>();
	                for (int i=1; i<=columnCount; i++){  
	                	
	                			columnname.add(rsmd.getColumnName(i).toString());
	                			
//	                    System.out.print(rsmd.getColumnName(i));  
//	                    System.out.print("(" + rsmd.getColumnTypeName(i) + ")");  
//	                    System.out.print(" | ");  
	                }  
//	                System.out.println();  
	                // 输出数据  
	                while (rs.next()){  
	                	HashMap<String,String> row=new HashMap<String,String>();
	                    for (int i=1; i<=columnCount; i++){  
	                    	row.put(columnname.get(i-1), rs.getString(i) );
//	                        System.out.print(rs.getString(i) + " | ");  
	                    }  
	                    resultdata.add(row);
	                    /*System.out.println();  */
	                }   
	            	}
	            
	            }catch (Exception e) {
	            	log.error(e.getMessage());
	            	return null;
	            }finally {
		        	
		            try {
//		            	stmt.close();
		            	rs.close();
						conn.close();
					} catch (SQLException e) {
						log.error("数据库查询关闭异常。");
					}
		        }
		return resultdata;
	}
	
	public void db(){

				 Connection conn = null;
			     String sql;
			        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
			        // 避免中文乱码要指定useUnicode和characterEncoding
			        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
			        // 下面语句之前就要先创建javademo数据库
			        String url = "jdbc:mysql://192.168.210.122:3306/test?"
			                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
			        
			        try {
			            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
			            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
			            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			            // or:
			            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			            // or：
			            // new com.mysql.jdbc.Driver();
			 
			            System.out.println("成功加载MySQL驱动程序");
			            // 一个Connection代表一个数据库连接
			            conn = DriverManager.getConnection(url);
			            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			            java.sql.Statement stmt = conn.createStatement();
			            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
			            int result = ((java.sql.Statement) stmt).executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
			            if (result != -1) {
			                System.out.println("创建数据表成功");
			                sql = "insert into student(NO,name) values('2012001','陶伟基')";
			                result = ((java.sql.Statement) stmt).executeUpdate(sql);
			                sql = "insert into student(NO,name) values('2012002','周小俊')";
			                result = ((java.sql.Statement) stmt).executeUpdate(sql);
			                sql = "select * from student";
			                ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
			                System.out.println("学号\t姓名");
			                while (rs.next()) {
			                    System.out
			                            .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
			                }
			            }
			        } catch (SQLException e) {
			            System.out.println("MySQL操作错误");
			            e.printStackTrace();
			        } catch (Exception e) {
			            e.printStackTrace();
			        } finally {
			        	
			            try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			        
}
			        public static void main(String[] args) throws SQLException {
			        
			        	DB db=new DB();
			        	db.db();
	}

}
