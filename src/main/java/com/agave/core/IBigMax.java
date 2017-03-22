package com.agave.core;

import java.util.List;

import com.agave.data.DataCell;

public interface IBigMax {

	IRestModel RestModel(String modelname);

	List<DataCell> ExcelData(String dataid);

	String setValue(String Dataid, String Value);

	TempValueBean getValue(String uuid);

	IGuiModel GuiModel(String modelname);

	ITcpModel TcpModel(String modelname);

}