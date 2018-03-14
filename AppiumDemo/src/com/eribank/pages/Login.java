package com.eribank.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import com.eribank.base.BaseEribank;
import com.eribank.utility.ReportUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Login {
	private static WebElement element;
    private static Properties prop;
    private static InputStream mapLoginObj;
    private static HashMap<String, String> hmap;
    //private HashMap<String, String> Loginmap;
    
    public static void setObjectsToMap()throws IOException {
    	element = null;
    	prop = new Properties();
    	mapLoginObj = BaseEribank.class.getResourceAsStream("/login.properties");
    	hmap = new HashMap<String, String>();
		prop.load(mapLoginObj);
		hmap.putAll(prop.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
    }
    
    private static WebElement textUsername(AndroidDriver<AndroidElement> driver)throws IOException  {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("userName"));
    	return element;
    }
    private static WebElement textPassword(AndroidDriver<AndroidElement> driver)throws IOException  {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("password"));
    	return element;
    }
    private static WebElement buttonLogin(AndroidDriver<AndroidElement> driver)throws IOException  {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("loginButton"));
    	return element;    	
    }
    
    public static Object doLogin(AndroidDriver<AndroidElement> driver, String userName, String password) throws Exception {
    	if(userName == "") {System.out.println("BLANK");}
    	ReportUtility.getInstance().addComments("Enter username and password");
    	textUsername(driver).sendKeys(userName);
    	textPassword(driver).sendKeys(password);
    	ReportUtility.getInstance().catureScreenshot(driver,"LogIn",100);
    	buttonLogin(driver).click();
    	return BaseEribank.getObject("com.eribank.pages.Dashboard");
    }

}
