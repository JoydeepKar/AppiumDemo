package com.salesforce.pages;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eribank.utility.ReportUtility;
import com.salesforce.base.BaseSalesforce;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Lead {
	private static WebDriverWait wait;
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
	private static JavascriptExecutor jse;
//private HashMap<String, String> Loginmap;

	public static void setObjectsToMap()throws IOException {
		element = null;
		prop = new Properties();
		mapLoginObj = BaseSalesforce.class.getResourceAsStream("/salesforceLead.properties");
		hmap = new HashMap<String, String>();
		prop.load(mapLoginObj);
		hmap.putAll(prop.entrySet().stream()
	            .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));	
	}
	
	private static WebElement buttonSaveLead(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("saveButton"));
		return element;    	
	}
	private static WebElement buttonNewLead(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("newButton"));
		return element;    	
	}
	private static WebElement selectLeadStatus(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("leadStatus"));
		return element;    	
	}
	private static WebElement LeadNew(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("New"));
		return element;    	
	}
	private static WebElement LeadWorking(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("Working"));
		return element;    	
	}
	private static WebElement LeadNurturing(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("Nurturing"));
		return element;    	
	}
	private static WebElement LeadQualified(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("Qualified"));
		return element;    	
	}

	
	
	
	private static WebElement selectNamePrefix(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefix"));
		return element;    	
	}
	private static WebElement namePrefixMr(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefixMr"));
		return element;    	
	}
	private static WebElement namePrefixMs(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefixMs"));
		return element;    	
	}
	private static WebElement namePrefixMrs(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefixMrs"));
		return element;    	
	}
	private static WebElement namePrefixDr(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefixDr"));
		return element;    	
	}
	private static WebElement namePrefixProf(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("namePrefixProf"));
		return element;    	
	}
	
	
	
	
	private static WebElement inputFirstName(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("firstName"));
		return element;    	
	}
	private static WebElement inputLastName(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("lastName"));
		return element;    	
	}
	private static WebElement inputEmail(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("email"));
		return element;    	
	}
	private static WebElement inputCompany(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("company"));
		return element;    	
	}

	public static Object createNewLead(AndroidDriver<AndroidElement> driver, String leadStatus, String prefix, String fname, String lname, String email, String company)throws Exception{
		new WebDriverWait(driver, 80)
        .until(ExpectedConditions.visibilityOf(buttonNewLead(driver)));
		buttonNewLead(driver).click();
		ReportUtility.getInstance().catureScreenshot(driver, "New Lead", 4000);
		Thread.sleep(5000);
		selectLeadStatus(driver).click();
		Thread.sleep(3000);
		
		if(leadStatus.contains("New")) {
			LeadNew(driver).click();
		}else if(leadStatus.contains("Working")) {
			LeadWorking(driver).click();
		}else if(leadStatus.contains("Nurturing")) {
			LeadNurturing(driver).click();
		}else if(leadStatus.contains("Qualified")) {
			LeadQualified(driver).click();
		}
		ReportUtility.getInstance().catureScreenshot(driver, "Select Lead Status", 4000);
		
		selectNamePrefix(driver).click();
		Thread.sleep(3000);
		
		if(prefix.contains("Mr.")) {
			namePrefixMr(driver).click();
		}else if(prefix.contains("Ms.")) {
			namePrefixMs(driver).click();
		}else if(prefix.contains("Mrs.")) {
			namePrefixMrs(driver).click();
		}else if(prefix.contains("Dr.")) {
			namePrefixDr(driver).click();
		}else if(prefix.contains("Prof.")) {
			namePrefixProf(driver).click();
		}
		Thread.sleep(3000);
		
		inputFirstName(driver).sendKeys(fname);
		inputLastName(driver).sendKeys(lname);	
		inputEmail(driver).sendKeys(email);
		ReportUtility.getInstance().catureScreenshot(driver, "Entered First Name and Last Name", 2000);
		
		new TouchAction((MobileDriver) driver).press(108, 1050).waitAction(Duration.ofMillis(2000)).moveTo(-108, 200).release().perform();

		Thread.sleep(2000);
		inputCompany(driver).sendKeys(company);
		ReportUtility.getInstance().catureScreenshot(driver, "Entered Company", 2000);
		buttonSaveLead(driver).click();
		Thread.sleep(4000);
		ReportUtility.getInstance().catureScreenshot(driver, "Lead Saved", 2000);
		
		return BaseSalesforce.getObject("com.salesforce.pages.Lead");
	}
}
