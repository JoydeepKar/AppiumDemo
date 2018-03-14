package com.salesforce.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eribank.utility.ReportUtility;
import com.salesforce.base.BaseSalesforce;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Lead {
	private static WebDriverWait wait;
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
	private static Select select;
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

	public static Object createNewLead(AndroidDriver<AndroidElement> driver, String leadStatus, String fname, String lname, String email)throws Exception{
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
		
//		inputFirstName(driver).click();
		inputFirstName(driver).sendKeys(fname);
//		inputLastName(driver).click();
		inputLastName(driver).sendKeys(lname);	
		inputEmail(driver).sendKeys(email);
		ReportUtility.getInstance().catureScreenshot(driver, "Entered First Name and Last Name", 2000);
		return BaseSalesforce.getObject("com.salesforce.pages.Lead");
	}
}
