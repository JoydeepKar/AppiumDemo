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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test_script_hire_employee1 {
	private WebDriver driver ;
	private StringBuffer verificationErrors = new StringBuffer();
	private WebElement element;
	private Actions action;
	private JavascriptExecutor jse;
	private FileInputStream fis = null;
	private BufferedReader br = null;
	private String line = "";
	private String cvsSplitBy = ",";
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private DataFormatter formatter;
	private int totanumberofcolumns;

	
	@Before
	public void setUp() throws Exception {
		
	

	//	System.setProperty("webdriver.gecko.driver","C:\\Users\\JoydeepKar\\Downloads\\geckodriver-v0.20.0-win64\\geckodriver.exe");
		//C:\Users\JoydeepKar\Downloads\chromedriver_win32
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\JoydeepKar\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public boolean retryingFindClick(By by) {
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 3) {
	        try {
	            driver.findElement(by).click();
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	    return result;
	}

	public boolean retryingFindInput(By by, String value) {
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 4) {
	        try {
	            driver.findElement(by).sendKeys(value);
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	    return result;
	}
	
	public void senseCursorState(WebDriver driver) throws InterruptedException {
			while(true) {
				if(driver.findElement(By.tagName("body")).getCssValue("cursor").equals("wait") || driver.findElement(By.tagName("body")).getCssValue("cursor").equals("progress")) {
					System.out.println("Waited for 6 secs as cursor was busy");
					Thread.sleep(6000);
				}else {
					System.out.println("cursor is not busy");
					break;
				}
		}
	}
	
	public int getRowCount(String filename, String sheetName) {
		try {
			fis = new FileInputStream(new File(filename));
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			Row rowkey = sheet.getRow(0);
			totanumberofcolumns = rowkey.getLastCellNum();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return sheet.getLastRowNum();
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
		//for(int i = 0;i<getRowCount("Data/data_HireEmployee.xls", "Sheet1");i++) {
			
		driver.get("https://efrx-test.login.ap4.oraclecloud.com/oam/server/obrareq.cgi?encquery%3DQn5RxkqBg9O0Yx%2Fjvi1vYueICFVYGJ8PlTl%2FqW51rzMaBg7%2BZV8CBY4Tsy8F%2BXL8UEZafoUq32FllBIjbfLMODAH9IcyNL%2BMRurAbzzJ2xFmQN7g4QkS7P9zRxq1zMxl1yoknxEsmvKwSZtwkPpEelgjvLQY8GA6nRfTh7PuQadV%2FZoFGGYlNIDPt5%2BLw46DdaxGCu958enCE1gBAFOhNM8VUMG65bB0jONqe38c%2Fg9stdGkAYdv2PmyJVjuDy%2BAo1E79R%2FfPBxXepNgQz9MNJN24glh%2Bi4oO%2FyhPsiBJGfHHTgulY8FuKWp0gRCE3WvQHHQB8LjVEiZhXwopOCUIfpndXb0oMUm9pfhHaCEySmKVIT1gktZmR23jlS%2BmdiEJ31Hb%2BR4Vx10ifL2AEB75%2B%2BGU1pdHS4ZeP4K%2BQn%2FbM41cHm47ELzBC%2B5lGs6LEKBrZNy%2FGOx4sI1cvX2Iq0RIqxgCPpNBJZyODyeJCxjl6XChOf5NFSgzcQG8Dfp5co23ZH0KQbHcvyqeCTV1vUgvA%3D%3D%20agentid%3DOraFusionApp_11AG%20ver%3D1%20crmethod%3D2&ECID-Context=1.005Pat7DqoX6mJSLmEH7iZ0003gP00008K%3BkXjE");
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jse = (JavascriptExecutor) driver;
		action = new Actions(driver);
		HashMap<String, ArrayList<String>> hash = readTestDataFile ("Data/data_HireEmployee.xls");
		
		 
		driver.findElement(By.xpath("//input[@id='userid']")).clear();
		driver.findElement(By.xpath("//input[@id='userid']")).sendKeys(hash.get("User ID").get(0));
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(hash.get("Password").get(0));
		takeSnapShot(driver, "Login");
		driver.findElement(By.xpath("//button[@id='btnActive']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//div[@id='pt1:_UISpgl53u']/div/a[@id='pt1:_UISmmLink']")))).get(0);
		//new Actions(driver).moveToElement(element).click().perform();
		retryingFindClick(By.xpath("//div[@id='pt1:_UISpgl53u']/div/a[@id='pt1:_UISmmLink']"));
		//Thread.sleep(10000);
		//driver.findElement(By.xpath("//a[@id='pt1:_UISmmLink']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//a[@id='pt1:nv_itemNode_workforce_management_new_person']")))).get(0);
		takeSnapShot(driver, "Menu");
		new Actions(driver).moveToElement(element).click().perform();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//a[@id='pt1:nv_itemNode_workforce_management_new_person']")).click();
		//driver.manage().timeouts().implicitlyWait(7,TimeUnit.SECONDS);
		//driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:_FOTsdiAddCwkDefaultRegional::icon']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:_FOTsdiAddCwkDefaultRegional::icon']")))).get(0);
		new Actions(driver).moveToElement(element).click().perform();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:_FOTRaT:0:RAtl1']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:_FOTRaT:0:RAtl1']")))).get(0);
		new Actions(driver).moveToElement(element).click().perform();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//button[@id='_FOd1::msgDlg::cancel']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//button[@id='_FOd1::msgDlg::cancel']")))).get(0);
		new Actions(driver).moveToElement(element).click().perform();
		//takeSnapShot(driver, "ABC");
		//Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:inputDate1::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:inputDate1::content']")).sendKeys(hash.get("Hire Date").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice1::content']"))).selectByVisibleText(hash.get("Hire Action").get(0));
		driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice1::content']")).sendKeys(Keys.ENTER);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice2::content']"))).selectByVisibleText(hash.get("Hire Reason").get(0));
		driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice2::content']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::content']")).sendKeys(hash.get("Legal Employer").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::content']")).sendKeys(Keys.ENTER);
		//Thread.sleep(5000);
		//Thread.sleep(5000);		
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::_afrautosuggestpopup']/li[1]")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::_afrautosuggestpopup']/li[1]")))).get(0);
		new Actions(driver).moveToElement(element).click().perform();
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:r1:0:it1::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:r1:0:it1::content']")))).get(0);
		//jse.executeScript("arguments[0].scrollIntoView();",element );
		//Thread.sleep(3000);
		Action seriesOfActions = action.moveToElement(element).click().sendKeys(element,hash.get("Person Number").get(0)).build();
		seriesOfActions.perform();
		//element.clear();
		System.out.println(hash.get("Person Number").get(0));
		//element.sendKeys(hash.get("Person Number").get(0));
		if(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:r1:0:it1::content']")).getAttribute("value").equals(hash.get("Person Number").get(0))) {
			System.out.println("Correctly inputted Person Number");
		}else {
			retryingFindInput(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:r1:0:it1::content']"), hash.get("Person Number").get(0));
			//element.sendKeys(hash.get("Person Number").get(0));			
		}
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:4:selectOneChoice4::content']")))).get(0);
		new Select(element).selectByVisibleText(hash.get("Title").get(0));
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:7:it20::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:7:it20::content']")).sendKeys(hash.get("Last Name").get(0));
		takeSnapShot(driver, "Personal Info");
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:7:it20::content']")).sendKeys(hash.get("Last Name").get(0));
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:5:it60::content']")).clear();
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:5:it60::content']")).sendKeys(hash.get("First Name").get(0));
		//Thread.sleep(5000);
		//new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:4:selectOneChoice4::content']"))).selectByVisibleText(hash.get("Title").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:soc3::content']"))).selectByVisibleText(hash.get("Gender").get(0));
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:id3::content']")))).get(0);
		element.clear();
		//new Actions(driver).sendKeys(hash.get("Date of Birth").get(0)).perform();
		element.sendKeys(hash.get("Date of Birth").get(0));
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:id3::content']")).clear();
		
		element.sendKeys(Keys.PAGE_DOWN);
		element.sendKeys(Keys.PAGE_DOWN);
		//System.out.println("197 passed");
		Thread.sleep(4000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:id3::content']")).sendKeys(hash.get("Date of Birth").get(0));
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:create::icon']")))).get(0);
		retryingFindClick(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:create::icon']"));
		//element.click();
		//jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		//new Actions(driver).click().perform();
		//driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:create::icon']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:iclov1::content']")))).get(0);
		element.clear();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:iclov1::content']")).clear();
		element.sendKeys(hash.get("Country").get(0));
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:iclov1::_afrautosuggestpopup']/li[1]")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:iclov1::_afrautosuggestpopup']/li[1]")))).get(0);
		retryingFindClick(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:iclov1::_afrautosuggestpopup']/li[1]"));
		//element.click();
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:soc2::content']"))).selectByVisibleText(hash.get("NationalIDType").get(0));
		driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:soc2::content']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:it1::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:it1::content']")).sendKeys(hash.get("National ID").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r2:0:AT2:_ATp:table2:0:it1::content']")).sendKeys(Keys.ENTER);
		//driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:tt1:next']/a/span")).click();
		takeSnapShot(driver, "National ID, Type");
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:tt1:next']/a/span")))).get(0);
		//element.click();
		retryingFindClick(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:tt1:next']/a/span"));
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:cb1']")).click();
		/*
		 * Pop up
		 */
		//element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:cb1']")))).get(0);
		//element.click();
		/*
		 * Ends
		 */
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:countrylov::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:countrylov::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:countrylov::content']")).sendKeys(hash.get("Country").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:0:inputText17::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:0:inputText17::content']")).sendKeys(hash.get("Address Line 1").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:1:inputText18::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:1:inputText18::content']")).sendKeys(hash.get("Address Line 2").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:3:inputText21::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:3:inputText21::content']")).sendKeys(hash.get("City").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:4:inputText27::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:4:inputText27::content']")).sendKeys(hash.get("Postal Code").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:5:inputText22::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:Perso1:0:Maint1:0:i1:5:inputText22::content']")).sendKeys(hash.get("State").get(0));
		takeSnapShot(driver, "Address Info");
		driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:create::icon']")).click();
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:soc1::content']"))).selectByVisibleText(hash.get("Type").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:iclov1::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:iclov1::content']")).sendKeys(hash.get("Country").get(0));
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:iclov1::_afrautosuggestpopup']/li[1]")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:iclov1::_afrautosuggestpopup']/li[1]")))).get(0);
		element.click();

		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:it2::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:it2::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:it2::content']")).sendKeys(hash.get("Area Code").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:it3::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r1:0:AT1:_ATp:table1:0:it3::content']")).sendKeys(hash.get("Number").get(0));
		seriesOfActions = action.moveToElement(driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:create::icon']"))).click().build();
		seriesOfActions.perform();
		//driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:create::icon']")).click();
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:table1:0:soc1::content
		//new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:table1:0:soc1::content']"))).selectByVisibleText("Work E-Mail");
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:table1:0:soc1::content']")))).get(0);
		new Select(element).selectByVisibleText("Work E-Mail");
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:table1:0:it1::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r2:0:AT1:_ATp:table1:0:it1::content']")).sendKeys(hash.get("E-Mail").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r3:0:soc2::content']"))).selectByVisibleText(hash.get("Marital Status").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r3:0:soc4::content']"))).selectByVisibleText(hash.get("Religion").get(0));
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:Perso2:0:pt_r3:0:hoc2::content']"))).selectByVisibleText(hash.get("Highest Education Level").get(0));
		driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:1:pt1:SP1:tt1:next']")).click();
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:businessUnitId::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:businessUnitId::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:businessUnitId::content']")).sendKeys(hash.get("Business unit").get(0));
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:businessUnitId::_afrautosuggestpopup']/li[1]")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:businessUnitId::_afrautosuggestpopup']/li[1]")))).get(0);
		//jse.executeScript("arguments[0].scrollIntoView();",element );
		element.click();
		//Thread.sleep(5000);
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:selectOneChoice1::content']")))).get(0);
		new Select(element).selectByVisibleText(hash.get("Person Type").get(0));
		//new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:NewPe1:0:selectOneChoice1::content']"))).selectByVisibleText(hash.get("Person Type").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:jobId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:jobId::content']")).sendKeys(hash.get("Job").get(0));
		if(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:jobId::content']")).getAttribute("value").equals(hash.get("Job").get(0))) {
			System.out.println("Correctly inputted Job");
		}else {
			retryingFindInput(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']"), hash.get("Grade").get(0));
			//element.sendKeys(hash.get("Grade").get(0));
			senseCursorState(driver);
		}
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:jobId::_afrautosuggestpopup']/li[1]")).click();
//		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:jobId::_afrautosuggestpopup']/li[1]")))).get(0);
		//jse.executeScript("arguments[0].scrollIntoView();",element );
		//element.click();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']")).sendKeys(hash.get("Grade").get(0));
		//Thread.sleep(8000);
		if(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']")).getAttribute("value").equals(hash.get("Grade").get(0))) {
			System.out.println("Correctly inputted Grade");
		}else {
			retryingFindInput(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']"), hash.get("Grade").get(0));
			//element.sendKeys(hash.get("Grade").get(0));
			senseCursorState(driver);
		}
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::content']")).sendKeys(Keys.END);
		//element.click();				
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::_afrautosuggestpopup']/li[1]")).click();
		//element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::_afrautosuggestpopup']/li[1]")))).get(0);
		//jse.executeScript("arguments[0].scrollIntoView();",element );
		//element.click();
		//retryingFindClick(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:gradeId::_afrautosuggestpopup']/li[1]"));
		System.out.println("323 passed");
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:departmentId::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:departmentId::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:departmentId::content']")).sendKeys(hash.get("Department").get(0));
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:departmentId::_afrautosuggestpopup']/li[1]")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:departmentId::_afrautosuggestpopup']/li[1]")))).get(0);
		//jse.executeScript("arguments[0].scrollIntoView();",element );
		element.click();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::content']")))).get(0);
		element.clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::content']")).sendKeys(hash.get("Location").get(0));
		if(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::content']")).getAttribute("value").equals(hash.get("Location").get(0))) {
			System.out.println("Location passed");
		}else {
			retryingFindInput(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::content']"), hash.get("Location").get(0));
		}
//		System.out.println("Passed 295");
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//ul[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:locationId::_afrautosuggestpopup']/li[1]")).click();
		//Thread.sleep(5000);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:selectOneRadio1::content']"))).selectByVisibleText(hash.get("Working at Home").get(0));
		senseCursorState(driver);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:selectOneChoice1::content']"))).selectByVisibleText(hash.get("Worker Category").get(0));
		senseCursorState(driver);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:selectOneChoice3::content']"))).selectByVisibleText(hash.get("Assignment Category").get(0));
		senseCursorState(driver);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:selectOneChoice2::content']"))).selectByVisibleText(hash.get("Hourly Paid or Salaried").get(0));
		senseCursorState(driver);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:soc1::content']"))).selectByVisibleText(hash.get("Full Time or part Time").get(0));
		senseCursorState(driver);
		new Select(driver.findElement(By.xpath("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:JobDe1:0:selectOneRadio2::content']"))).selectByVisibleText(hash.get("Working as Manager").get(0));
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r3:0:i1:0:ManagerNameId::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r3:0:i1:0:ManagerNameId::content']")).sendKeys(hash.get("Name").get(0));
		senseCursorState(driver);
		System.out.println("406 passed");
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r3:0:i1:0:ManagerNameId::_afrautosuggestpopup']/li[1]")).click();
		//Thread.sleep(5000);
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:cil113::icon']")))).get(0);
		element.click();
		System.out.println("416 passed");
		
		//driver.findElement(By.xpath("//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:cil113::icon']")).click();
		//Thread.sleep(5000);
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:table1:0:selectOneChoice1::content']")))).get(0);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:table1:0:selectOneChoice1::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:table1:0:selectOneChoice1::content']")).sendKeys(hash.get("Payroll").get(0));
		if(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:table1:0:selectOneChoice1::content']")).getAttribute("value").equals(hash.get("Payroll").get(0))) {
			System.out.println("Payroll passed");
		}else {
			retryingFindInput(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe3:0:r1:0:AT1:_ATp:table1:0:selectOneChoice1::content']"), hash.get("Payroll").get(0));
			senseCursorState(driver);
		}

		
		//Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:tt1:next']")).click();
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r1:0:r5:0:idSalaryBasis::content']")).clear();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r1:0:r5:0:idSalaryBasis::content']")))).get(0);
		element.clear();
		element.sendKeys(hash.get("Salary Basis").get(0));
		//driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r1:0:r5:0:idSalaryBasis::content']")).sendKeys(hash.get("Salary Basis").get(0));
		//Thread.sleep(5000);
		senseCursorState(driver);
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r1:0:r5:0:idSalaryAmount::content']")).clear();
		driver.findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r1:0:r5:0:idSalaryAmount::content']")).sendKeys(hash.get("Salary Amount").get(0));
		driver.findElement(By.xpath("//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:r6:0:ph2::_afrDscl']")).click();
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:tt1:next']")))).get(0);
		element.click();
		senseCursorState(driver);
		System.out.println("454 passed");
		//driver.findElement(By.xpath("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:3:pt1:SP1:tt1:next']")).click();
		//Thread.sleep(5000);
		//Thread.sleep(5000);
		
		driver.findElement(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:submit']/a")).click();
		senseCursorState(driver);
		String submit = Keys.chord(Keys.ALT, "y");
		driver.findElement(By.tagName("html")).sendKeys(submit);
		senseCursorState(driver);
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:okConfirmationDialog']")))).get(0);
		element.click();
		senseCursorState(driver);
		//_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:confirmationDialog::_ttxt
		//_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:confirmationDialog::_ttxt
		//submit = Keys.chord(Keys.ALT, "o");
		//driver.findElement(By.tagName("html")).sendKeys(submit);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//*[@id='_Fopt1:_FOr1:1:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:okWarningDialog']")).click();
		//senseCursorState(driver);
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//button[@id='_Fopt1:_FOr1:1:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:4:pt1:AP1:tt1:okConfirmationDialog']")).click();
		//}
	}
}