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

public class Lead {
	private static WebDriverWait wait;
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
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
	public static Object clickNewLead(AndroidDriver<AndroidElement> driver)throws Exception{
		buttonNewLead(driver).click();
		return BaseSalesforce.getObject("com.salesforce.pages.Lead");
	}
}
