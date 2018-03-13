package com.eribank.testcase;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eribank.base.BaseEribank;
import com.eribank.pages.Dashboard;
import com.eribank.pages.Login;
import com.eribank.pages.MakePayment;
import com.eribank.utility.ExcelUtility;
import com.eribank.utility.ReportUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestCaseMakePayment {
	private AndroidDriver<AndroidElement> driver;
	BaseEribank obj = BaseEribank.getInstance();
	HashMap<String, ArrayList<String>> loginData,paymentData;
	int rowCount;
 
 @Before
 public void setUp() throws MalformedURLException {
	 		driver = obj.getBase();
	 		loginData = ExcelUtility.readTestDataFile ("Data/DemoData.xls", "Login");
	 		paymentData = ExcelUtility.readTestDataFile ("Data/DemoData.xls", "MakePayment");
	 		rowCount = ExcelUtility.getRowCount("Data/DemoData.xls", "MakePayment");
	 		ReportUtility.getInstance().doReportSetup();
 }
 
@Test
 public void makePayment()throws Exception {
	for(int row = 0;row<rowCount;row++) {
		ReportUtility.getInstance().startTest("Make Payment", "This is to make a payment");
		ReportUtility.getInstance().addComments("Script starts from here");
		((Dashboard)((MakePayment)((Dashboard)Login.doLogin(driver, loginData.get("username").get(row), loginData.get("password").get(row)))
				.clickButtonMakePayment(driver))
				.doMakePayment(driver,paymentData.get("phone").get(row), paymentData.get("name").get(row), paymentData.get("amount").get(row)))
				.clickButtonLogout(driver);
		ReportUtility.getInstance().addComments("Script ends here");
	}
	ReportUtility.getInstance().endSetup();
 }
 
 @After
 public void tearDown() {
	 		driver.quit();
 }
}
