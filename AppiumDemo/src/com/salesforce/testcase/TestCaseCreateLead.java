package com.salesforce.testcase;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.salesforce.base.BaseSalesforce;
import com.salesforce.pages.Dashboard;
import com.salesforce.pages.Lead;
import com.eribank.utility.ExcelUtility;
import com.eribank.utility.ReportUtility;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestCaseCreateLead {
	private AndroidDriver<AndroidElement> driver;
	BaseSalesforce obj = BaseSalesforce.getInstance();
	//HashMap<String, ArrayList<String>> loginData,paymentData;
	HashMap<String, ArrayList<String>> createLead;
	int rowCount;
 
	 @Before
	 public void setUp() throws MalformedURLException {
		 		driver = obj.getBase();
		 		//loginData = ExcelUtility.readTestDataFile ("Data/DemoData.xls", "Login");
		 		createLead = ExcelUtility.readTestDataFile ("Data/DemoDataSalesforce.xls", "CreateLead");
		 		rowCount = ExcelUtility.getRowCount("Data/DemoDataSalesforce.xls", "CreateLead");
		 		ReportUtility.getInstance().doReportSetup();
	 }
	 
	@SuppressWarnings("static-access")
	@Test
	 public void createLead()throws Exception {
		for(int row = 0;row<rowCount;row++) {
			ReportUtility.getInstance().startTest("Create Lead", "This is to create a new Lead");
			ReportUtility.getInstance().addComments("Script starts from here");
			 ((Lead)Dashboard.selectLeadfromMenu(driver))
			.createNewLead(driver, createLead.get("leadstatus").get(row),createLead.get("firstname").get(row),createLead.get("lastname").get(row),createLead.get("email").get(row));
			ReportUtility.getInstance().addComments("Script ends here");
		}
		ReportUtility.getInstance().endSetup();
	 }
	 
	 @After
	 public void tearDown() {
		 		driver.quit();
	 }
}
