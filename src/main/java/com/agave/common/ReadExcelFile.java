 package com.agave.common;
 
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.testng.log4testng.Logger;

 
 public class ReadExcelFile
  
 {
   private  Logger log = Logger.getLogger(ReadExcelFile.class);
   
   public Workbook getCaseFiles(String filePath)
   {
	 Workbook  wb = null;
     FileInputStream inp1 = null;
     try
     {
       inp1 = new FileInputStream(filePath);
       
       wb = WorkbookFactory.create(inp1);
       if (null != inp1) {
         try
         {
           inp1.close();
         }
         catch (IOException e)
         {
           this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e.getMessage() }));
           e.printStackTrace();
         }
       }
       return wb;
     }
     catch (FileNotFoundException e)
     {
       this.log.error(String.format("没有找到指定路径[%s]下的文件 [FileNotFoundException:%s]", new Object[] { filePath, e.getMessage() }));
       
 
       e.printStackTrace();
       if (null != inp1) {
         try
         {
           inp1.close();
         }
         catch (IOException e1)
         {
           this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e.getMessage() }));
           
 
           e1.printStackTrace();
         }
       }
     }
     catch (InvalidFormatException e1)
     {
       this.log.error(String.format("指定路径下[%s]的文件的格式错误，请检查是否为excel文件[InvalidFormatException:%s]", new Object[] { filePath, e1.getMessage() }));
       
 
       e1.printStackTrace();
       if (null != inp1) {
         try
         {
           inp1.close();
         }
         catch (IOException e)
         {
           this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e.getMessage() }));
           
 
           e.printStackTrace();
         }
       }
     }
     catch (IOException e)
     {
       this.log.error(String.format("在读取指定路径下%s的文件的过程中出现异常[IOException:%s]", new Object[] { filePath, e.getMessage() }));
      
      e.printStackTrace();
      if (null != inp1) {
        try
        {
          inp1.close();
        }
        catch (IOException e1)
        {
          this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e.getMessage() }));
           
 
           e1.printStackTrace();
         }
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
       this.log.error(String.format("在读取指定路径下%s的文件的过程中出现异常[Exception:%s]", new Object[] { filePath, e.getMessage() }));
       if (null != inp1) {
         try
         {
           inp1.close();
         }
         catch (IOException e1)
         {
           this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e1.getMessage() }));
          

          e1.printStackTrace();
        }
      }
    }
    finally
    {
      if (null != inp1) {
        try
        {
          inp1.close();
        }
        catch (IOException e)
        {
          this.log.error(String.format("在关闭读取文件的输入流的过程中出现异常[IOException:%s]", new Object[] { e.getMessage() }));
           
 
           e.printStackTrace();
         }
       }
     }
	return wb;
   }
 }
