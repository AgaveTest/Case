 package com.agave.data;
 
 public final class DataCell
   implements Cloneable
 {
   public static final DataCell ONE = new DataCell();
   private String titleName;
   private String dataValue;
   
   public String getTitleName()
   {
     return this.titleName;
   }
   
   public void setTitleName(String titleName)
   {
     this.titleName = titleName;
   }
   
   public String getDataValue()
   {
     return this.dataValue;
   }
   
   public void setDataValue(String dataValue)
   {
     this.dataValue = dataValue;
   }
   
   public DataCell clone()
   {
     try
     {
       return (DataCell)super.clone();
     }
     catch (CloneNotSupportedException e)
     {
       e.printStackTrace();
     }
     return new DataCell();
   }
   
   public static DataCell clone(String titleName, String dataValue)
   {
     DataCell dataCell = ONE.clone();
     if (null == dataCell) {
       dataCell = new DataCell();
     }
     dataCell.setDataValue(dataValue);
     dataCell.setTitleName(titleName);
     
     return dataCell;
   }
   
   public String toString()
   {
     return "DataCell [titleName=" + this.titleName + ", dataValue=" + this.dataValue + "]";
   }
 }

