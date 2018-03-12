package com.eribank.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import com.eribank.base.BaseEribank;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Dashboard {	
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
//private HashMap<String, String> Loginmap;

public static void setObjectsToMap()throws IOException {
	element = null;
	prop = new Properties();
	mapLoginObj = BaseEribank.class.getResourceAsStream("/dashboard.properties");
	hmap = new HashMap<String, String>();
	prop.load(mapLoginObj);
	hmap.putAll(prop.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));	
}

private static WebElement buttonMakePayment(AndroidDriver<AndroidElement> driver)throws IOException {
	setObjectsToMap();
	element = driver.findElementByXPath(hmap.get("makePaymentButton"));
	return element;    	
}
private static WebElement buttonLogout(AndroidDriver<AndroidElement> driver)throws IOException {
	setObjectsToMap();
	element = driver.findElementByXPath(hmap.get("logoutButton"));
	return element;    	
}
public Object clickButtonMakePayment(AndroidDriver<AndroidElement> driver)throws Exception{
	buttonMakePayment(driver).click();
	return BaseEribank.getObject("com.eribank.pages.MakePayment");
}
public Object clickButtonLogout(AndroidDriver<AndroidElement> driver)throws Exception{
	buttonLogout(driver).click();
	return BaseEribank.getObject("com.eribank.pages.Login");
}



}
