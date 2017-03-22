 package com.agave.common;
 
 import java.util.Date;
 import org.apache.poi.ss.usermodel.Cell;
 import org.apache.poi.ss.usermodel.DateUtil;
import org.testng.log4testng.Logger;
 
 public class TranslateCellValueToStr
 {
   private final Logger log = Logger.getLogger(TranslateCellValueToStr.class);
   
   public String changeCellValueToStr(Cell cell)
   {
     String returnValue = "";
     if (null == cell)
     {
       this.log.warn(String.format("未初始化数据格中的数据默认为空字符串", new Object[0]));
       return "";
     }
     switch (cell.getCellType())
     {
     case 1: 
       returnValue = cell.getRichStringCellValue() + "";
       break;
     case 0: 
       if (DateUtil.isCellDateFormatted(cell))
       {
         String number = cell.getDateCellValue().toString();
         
         returnValue = number;
       }
       else
       {
         String number = changeNumber(cell.getNumericCellValue() + "");
        
         returnValue = number;
       }
       break;
     case 4: 
       returnValue = cell.getBooleanCellValue() + "";
       break;
     case 2: 
       returnValue = cell.getCellFormula();
       break;
     case 3: 
     default: 
       returnValue = "";
     }
     return returnValue;
   }
   
   private String changeNumber(String number)
   {
     String rnumber = "";
     
     Double b = Double.valueOf(Double.parseDouble(number));
     if (b.doubleValue() % 1.0D > 0.0D)
     {
       rnumber = number;
     }
     else
     {
       StringBuffer c = new StringBuffer(b.toString());
       int endof = c.indexOf(".");
       int cl = c.length();
       c.delete(endof, cl);
       rnumber = c.toString();
     }
     return rnumber;
   }
 }

