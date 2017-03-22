package test.agave;

import org.testng.annotations.Test;

import com.agave.common.ReadCommdata;

public class ReadCDTest {
	
	@Test
	public void testrcd(){
		
		System.out.println(ReadCommdata.getInstance().getCommondata());
	}

}
