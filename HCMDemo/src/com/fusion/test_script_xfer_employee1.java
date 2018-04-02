package com.fusion;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.fusion.utility.HCMUtility;

public class test_script_xfer_employee1 {
	private WebDriver driver ;
	private StringBuffer verificationErrors = new StringBuffer();
	private WebElement element;
	
	@Before
	public void setUp() throws Exception {
		
	

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\JoydeepKar\\Downloads\\chromedriver_win32\\chromedriver.exe");	
		driver = new ChromeDriver();
		
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		
	}

	@After
	public void tearDown() throws Exception {
		//driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public HashMap<String, ArrayList<String>> readTestDataFile (String filename) {
		FileInputStream fis = null;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
		try {
			fis = new FileInputStream(new File(filename));
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			int totanumberofcolumns = 0;
			Row rowkey = sheet.getRow(0);
			totanumberofcolumns = rowkey.getLastCellNum();
			for (int rowindex = 1; rowindex <= sheet.getLastRowNum(); rowindex++) {
				Row rowvalue = sheet.getRow(rowindex);
				String key = null;
				for (int colindex = 0; colindex < totanumberofcolumns; colindex++) {
					Cell cellkey = rowkey.getCell(colindex);
					key = formatter.formatCellValue(cellkey);
					String value = null;
					Cell cellvalue = rowvalue.getCell(colindex);
					value = formatter.formatCellValue(cellvalue);
					if (!values.containsKey(key)) {
						ArrayList<String> valuearray = new ArrayList<String>();
						valuearray.add(value);
						values.put(key, valuearray);
						}
					else {
						values.get(key).add(value);
					}
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		return values;
	}

	public void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
		String filename = fileWithPath+"_"+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File("Screens/"+filename+".png");
		FileUtils.copyFile(SrcFile, DestFile);
	}

	
	@Test
	public void test_case_2() throws Exception {
		test_case_3();
	}
	public void test_case_3() throws Exception {
		driver.get("https://efrx-test.login.ap4.oraclecloud.com/oam/server/obrareq.cgi?encquery%3DQn5RxkqBg9O0Yx%2Fjvi1vYueICFVYGJ8PlTl%2FqW51rzMaBg7%2BZV8CBY4Tsy8F%2BXL8UEZafoUq32FllBIjbfLMODAH9IcyNL%2BMRurAbzzJ2xFmQN7g4QkS7P9zRxq1zMxl1yoknxEsmvKwSZtwkPpEelgjvLQY8GA6nRfTh7PuQadV%2FZoFGGYlNIDPt5%2BLw46DdaxGCu958enCE1gBAFOhNM8VUMG65bB0jONqe38c%2Fg9stdGkAYdv2PmyJVjuDy%2BAo1E79R%2FfPBxXepNgQz9MNJN24glh%2Bi4oO%2FyhPsiBJGfHHTgulY8FuKWp0gRCE3WvQHHQB8LjVEiZhXwopOCUIfpndXb0oMUm9pfhHaCEySmKVIT1gktZmR23jlS%2BmdiEJ31Hb%2BR4Vx10ifL2AEB75%2B%2BGU1pdHS4ZeP4K%2BQn%2FbM41cHm47ELzBC%2B5lGs6LEKBrZNy%2FGOx4sI1cvX2Iq0RIqxgCPpNBJZyODyeJCxjl6XChOf5NFSgzcQG8Dfp5co23ZH0KQbHcvyqeCTV1vUgvA%3D%3D%20agentid%3DOraFusionApp_11AG%20ver%3D1%20crmethod%3D2&ECID-Context=1.005Pat7DqoX6mJSLmEH7iZ0003gP00008K%3BkXjE");
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//action = new Actions(driver);
		HashMap<String, ArrayList<String>> hash = readTestDataFile ("Data/data_TransferingEmployee.xls");
		
		HCMUtility.retryingFindInput(By.xpath("//input[@id='userid']"), hash.get("User ID").get(0), driver);
		HCMUtility.retryingFindInput(By.xpath("//input[@id='password']"), hash.get("Password").get(0), driver);
		//driver.findElement(By.xpath("//input[@id='userid']")).clear();
		//driver.findElement(By.xpath("//input[@id='userid']")).sendKeys(hash.get("User ID").get(0));
		//element = HCMUtility.waitForObject(By.xpath("//input[@id='password']"));
		//element.sendKeys(hash.get("Password").get(0));
		//driver.findElement(By.xpath("//input[@id='password']")).clear();
		//driver.findElement(By.xpath("//input[@id='password']")).sendKeys(hash.get("Password").get(0));
		takeSnapShot(driver, "Login");
		driver.findElement(By.xpath("//button[@id='btnActive']")).click();
		HCMUtility.senseCursorState(driver);
		//Thread.sleep(5000);
		element = HCMUtility.waitForObject(By.xpath("//a[@id='pt1:_UISmmLink']"),driver);
		element.click();
		//driver.findElement(By.xpath("//a[@id='pt1:_UISmmLink']")).click();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//a[@id='pt1:nv_itemNode_workforce_management_person_management']")).click();
		element = HCMUtility.waitForObject(By.xpath("//a[@id='pt1:nv_itemNode_workforce_management_person_management']"), driver);
		element.click();
		//Thread.sleep(5000);	
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:q1:value00::content']")).clear();
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:q1:value00::content']")).sendKeys(hash.get("Name").get(0));
		HCMUtility.inputUsingAction(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:q1:value00::content']"), driver, hash.get("Name").get(0));
		element = HCMUtility.waitForObject(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:q1::search']"), driver);
		element.click();
		HCMUtility.senseCursorState(driver);
		//driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:q1::search']")).click();
		//Thread.sleep(5000);
		element = HCMUtility.waitForObject(By.xpath("//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:table1:_ATp:table2:0:gl1']"), driver);
		element.click();
		//driver.findElement(By.xpath("//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Perso1:0:SP3:table1:_ATp:table2:0:gl1']")).click();
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//td[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:edit::popArea']")).click();
		element = HCMUtility.waitForObject(By.xpath("//td[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:edit::popArea']"), driver);
		element.click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//tr[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:updBtn']/td[2]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:effectiveDate::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:effectiveDate::content']")).sendKeys(hash.get("Effective Start Date").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:actionsName1::content']"))).selectByVisibleText(hash.get("Action").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:actionsName1::content']"))).selectByVisibleText(hash.get("Action").get(0));
		driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:0:AP1:cb10']")).click();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:jobId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:jobId::content']")).sendKeys(hash.get("Job").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:gradeId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:gradeId::content']")).sendKeys(hash.get("Grade").get(0));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:gradeId::_afrautosuggestpopup']/li[1]")).click();
		Thread.sleep(5000);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:departmentId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:departmentId::content']")).sendKeys(hash.get("Department").get(0));                                         
		Thread.sleep(5000);
		driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:departmentId::_afrautosuggestpopup']/li[1]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:locationId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:locationId::content']")).sendKeys(hash.get("Location").get(0));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:JobDe1:0:locationId::_afrautosuggestpopup']/li[1]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:1:r:tt1:review']/a/span")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:2:AP1:tt1:submit']/a/span")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:2:AP1:tt1:okWarningDialog']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_person_management:0:MAt1:0:pt1:Manag1:2:AP1:tt1:okConfirmationDialog']")).click();
        
        



	
	
	}
}