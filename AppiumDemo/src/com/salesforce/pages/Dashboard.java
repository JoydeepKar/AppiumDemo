package com.salesforce.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eribank.utility.ReportUtility;
import com.salesforce.base.BaseSalesforce;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Dashboard {
	private static WebDriverWait wait;
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
//private HashMap<String, String> Loginmap;

	public static void setObjectsToMap()throws IOException {
		element = null;
		prop = new Properties();
		mapLoginObj = BaseSalesforce.class.getResourceAsStream("/salesforceDashboard.properties");
		hmap = new HashMap<String, String>();
		prop.load(mapLoginObj);
		hmap.putAll(prop.entrySet().stream()
	            .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));	
	}
	
	private static WebElement buttonHamburgerMenu(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("hamburgerMenu"));
		return element;    	
	}
	private static WebElement buttonLeadMenu(AndroidDriver<AndroidElement> driver)throws IOException {
		setObjectsToMap();
		element = driver.findElementByXPath(hmap.get("leadButton"));
		return element;    	
	}

	public static Object selectLeadfromMenu(AndroidDriver<AndroidElement> driver)throws Exception{
		Thread.sleep(6000);
		//System.out.println("waited 6 sec");
		buttonHamburgerMenu(driver).click();
		//System.out.println("menu clicked");
		ReportUtility.getInstance().catureScreenshot(driver, "All Menus", 4000);
		wait = new WebDriverWait(driver,20);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(hmap.get("leadButton")))); 
		buttonLeadMenu(driver).click();
		ReportUtility.getInstance().catureScreenshot(driver, "Recent Leads", 4000);
		return BaseSalesforce.getObject("com.salesforce.pages.Lead");
	}
}
