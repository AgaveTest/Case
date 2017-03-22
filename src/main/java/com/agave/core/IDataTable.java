package com.agave.core;

import java.util.ArrayList;
import java.util.List;

import com.agave.common.AgaveData;
import com.agave.data.DataCell;
public interface IDataTable {

	List<DataCell> getDataNameTolist(String datasoruce, String dataid);
	//输入数据源，以及数据定位标示

	List<DataCell> getDataNameTolis(String dataid);
	
}
