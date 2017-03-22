package test.agave;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.agave.core.IReturnData;
import com.agave.core.Agave;

public class AgaveTest {
//	@Test
//	public void isInitTest() {
//		// 测试是否再testng启动时启动
//		Assert.assertEquals(Agave.getInstance().getFirstMaker(), "org.testng.remote.RemoteTestNG");
//	}

//	@Test
//	public void runTest() {
//		// agave.getInstance().Model("dafult").send("datasheet.L001");//父类
//		// agave.getInstance().RestModel("dafult").send("datasheet.L001");
//		IReturnData rd = Agave.getInstance().RestModel("jenkinis_copy_project").send("datasheet.L001");
//	}

	@Test
	public void testGetJenkinsJob() {
		Agave.getInstance().RestModel("Jenkins-getjob").send("");
	}

//	@Test
//	public void setValue() {
//
//		String uuid = Agave.getInstance().setValue("name", "Agate");
//		IReturnData rd = Agave.getInstance().RestModel("jenkinis_copy_project").send("datasheet.L001", uuid);
//	}
}
