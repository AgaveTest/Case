package com.agave.data;

import java.lang.reflect.Array;
//import com.huawei.inoc2012.ski.io.analyze.TranslateCellValueToStr;
//import com.huawei.inoc2012.ski.io.file.bean.DataCell;
//import com.huawei.inoc2012.ski.log.LogFactory;
//import com.huawei.inoc2012.ski_interface.log.ILog;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.log4testng.Logger;

import com.agave.common.ADType;
import com.agave.common.APar;
import com.agave.common.AgaveData;
import com.agave.common.ReadExcelFile;
import com.agave.common.TranslateCellValueToStr;
import com.agave.core.AgaveCommonData;
import com.agave.core.IDataTable;
import com.agave.core.IRemoteData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




public class ReadDataTable implements IDataTable
{
  private  Logger log = Logger.getLogger(ReadDataTable.class);
  private TranslateCellValueToStr cellToStr = new TranslateCellValueToStr();//待修改
  private IRemoteData ad=new AgaveData();
  
  
//  删除 datatable 不与通用数据之间关联
  //public AgaveData getDataName(String datasoruce, String dataid){
//	  
//	  ad.setHeadValue(APar.name, "ExcelData");
//	  ad.setHeadValue(APar.a_type, ADType.DATATABLE.toString());
//	  ad.setHeadValue(APar.a_class, this.getClass().getName());
//	  
//	  ArrayList<DataCell> datalist=(ArrayList<DataCell>) this.getDataNameTolist(datasoruce,dataid);
//	  JSONObject jsonobject = JSONObject.fromObject("{}"); 
//	  for(DataCell dc:datalist){
//		  jsonobject.accumulate(dc.getTitleName(), dc.getDataValue());
//	  }
//	  ad.setBody(jsonobject);
//	  return ad;
//	  
//  }
  public List<DataCell> getDataNameTolist(String datasoruce, String dataid) {
  	 ReadExcelFile ref=new ReadExcelFile();
  	  Workbook wb=ref.getCaseFiles(datasoruce);
  	return this.getDataName(wb, dataid);
  }
//  //默认地址
//  public AgaveData getDataName(String dataid) {
//	  return this.getDataName(FILEPATH,dataid);
//  }
  public List<DataCell> getDataNameTolis(String dataid){
	  return this.getDataNameTolist(AgaveCommonData.FILEPATH, dataid);
	  
  }
  public List<DataCell> getDataName(Workbook caseFileWK, String dataName)
  {
    if (!checkTableName(dataName)) {
      return null;
    }
    this.log.debug(String.format("获取数据表中%s位置的数据信息...", new Object[] { dataName }));
    
    String[] values = dataName.split("\\.");
    Sheet sheet = caseFileWK.getSheet(values[0]);
    if (null == sheet) {
      return null;
    }
    List<DataCell> dataCellList = null;
    if (2 == values.length) {
      dataCellList = getDataCellRowList(sheet, values[1]);
    } else if (3 == values.length) {
      dataCellList = getDataCellTitleList(sheet, values[1], values[2]);
    } else {
      return null;
    }
    if ((null != dataCellList) && (0 == dataCellList.size())) {
      this.log.warn(String.format("在数据表指定位置[%s]没有获取到有效的数据信息", new Object[] { dataName }));
     }
     return dataCellList;
   }
   
   private List<DataCell> getDataCellRowList(Sheet sheet, String dataId)
   {
     List<DataCell> dataList = new ArrayList();
     for (Row row : sheet) {
       if (0 == row.getFirstCellNum()) {
         if (dataId.equals(this.cellToStr.changeCellValueToStr(row.getCell(0))))
         {
           for (Cell cell : row) {
             if ((null != cell) && (0 != cell.getColumnIndex()) && (1 != cell.getColumnIndex()))
             {
               String data = this.cellToStr.changeCellValueToStr(cell);
               
               Cell titleCell = sheet.getRow(0).getCell(cell.getColumnIndex());
               if ((null != titleCell) && (!StringUtils.isBlank(this.cellToStr.changeCellValueToStr(titleCell))))
               {
                 String title = this.cellToStr.changeCellValueToStr(titleCell);
                 
                 dataList.add(DataCell.clone(title, data));
               }
             }
           }
           break;
         }
       }
     }
     return dataList;
   }
   
   private int getTitleIndex(Sheet sheet, String titleName)
   {
     int titelNum = 0;
     for (Cell cell : sheet.getRow(0))
     {
       if (titleName.equals(this.cellToStr.changeCellValueToStr(cell))) {
         return titelNum;
       }
       titelNum++;
     }
     return -1;
   }
   
   private List<DataCell> getDataCellTitleList(Sheet sheet, String dataId, String titleName)
   {
     List<DataCell> dataList = new ArrayList();
     
     int titelNum = getTitleIndex(sheet, titleName);
     if (titelNum < 0)
     {
       this.log.error(String.format("在%s页中不存在名称为%s标题的列", new Object[] { sheet.getSheetName(), titleName }));
       return dataList;
     }
     for (Row row : sheet) {
       if (0 == row.getFirstCellNum()) {
         if (dataId.equals(this.cellToStr.changeCellValueToStr(row.getCell(0))))
         {
           Cell cell = row.getCell(titelNum);
           if (null == cell)
           {
             this.log.error(String.format("在%s页中不存在第%s行标题列为%s的数据格信息", new Object[] { sheet.getSheetName(), Integer.valueOf(row.getRowNum()), titleName }));
             return dataList;
           }
           String data = this.cellToStr.changeCellValueToStr(cell);
           
           dataList.add(DataCell.clone(titleName, data));
           
           break;
         }
       }
     }
     return dataList;
   }
   
   private boolean checkTableName(String dataName)
   {
     if (StringUtils.isBlank(dataName)) {
       return false;
     }
     String regex = "^\\w+\\.\\w+(\\.\\w+)?$";
     return dataName.trim().matches(regex);
   }



 }


