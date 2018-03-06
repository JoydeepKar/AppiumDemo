package com.eribank.testcase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eribank.base.BaseEribank;

import com.eribank.pages.MakePayment;
import com.eribank.utility.ExcelUtility;
import com.eribank.pages.Dashboard;
import com.eribank.pages.Login;

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
 }
 
@Test
 public void makePayment()throws IOException {
	for(int row = 0;row<rowCount;row++) {
		
		/*
		 * Login starts here
		 * 	
		 */
			Login.textUsername(driver).sendKeys(loginData.get("username").get(row));
			Login.textPassword(driver).sendKeys(loginData.get("password").get(row));
			Login.buttonLogin(driver).click();
		/*
		 * 
		 * Login ends here
		 */
			
		/*
		 * 
		 * 	Dashboard, click on Make Payment
		 */
			Dashboard.buttonMakePayment(driver).click();
			
		/*
		 * 
		 * Make payment starts here	
		 */
			MakePayment.textPhone(driver).sendKeys(paymentData.get("phone").get(row));
			MakePayment.textName(driver).sendKeys(paymentData.get("name").get(row));
			MakePayment.textAmount(driver).sendKeys(paymentData.get("amount").get(row));
			MakePayment.selectCountry(driver).click();
			MakePayment.buttonClickGermay(driver).click();
			MakePayment.buttonsendPayment(driver).click();
			MakePayment.buttonYes(driver).click();
		/*
		 * 
		 * Make payment ends here	
		 */
			
		/*
		 * 
		 * Logout starts here
		 */
			Dashboard.buttonLogout(driver).click();
		/*
		 * 
		 * Logout ends here
		 */
	}
 }
 
 @After
 public void tearDown() {
	 		driver.quit();
 }
}
