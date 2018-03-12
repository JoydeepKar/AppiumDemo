package com.eribank.testcase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
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
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
 
 @Before
 public void setUp() throws MalformedURLException {
	 		driver = obj.getBase();
	 		loginData = ExcelUtility.readTestDataFile ("Data/DemoData.xls", "Login");
	 		paymentData = ExcelUtility.readTestDataFile ("Data/DemoData.xls", "MakePayment");
	 		rowCount = ExcelUtility.getRowCount("Data/DemoData.xls", "MakePayment");
	 		ReportUtility.getInstance().doReportSetup();
	 		
//	 		htmlReporter = new ExtentHtmlReporter("Reports/extent.html");
//	 		extent = new ExtentReports();
//	 		extent.attachReporter(htmlReporter);
//	 		htmlReporter.setAppendExisting(true);
 }
 
@Test
 public void makePayment()throws Exception {
	for(int row = 0;row<rowCount;row++) {
		ReportUtility.getInstance().startTest("Make Payment", "This is to make a payment");
//	    test = extent.createTest("Make Payment", "This is to make a payment");
//		test.assignAuthor("Joydeep Kar");
//	    test.info("Script starts from here");
		ReportUtility.getInstance().addComments("Script starts from here");
		((Dashboard)((MakePayment)((Dashboard)Login.doLogin(driver, loginData.get("username").get(row), loginData.get("password").get(row)))
				.clickButtonMakePayment(driver))
				.doMakePayment(driver,paymentData.get("phone").get(row), paymentData.get("name").get(row), paymentData.get("amount").get(row)))
				.clickButtonLogout(driver);
//		test.info("Script ends from here");
		ReportUtility.getInstance().addComments("Script ends here");
	}
//    extent.flush();
	ReportUtility.getInstance().endSetup();
 }
 
 @After
 public void tearDown() {
	 		driver.quit();
 }
}
