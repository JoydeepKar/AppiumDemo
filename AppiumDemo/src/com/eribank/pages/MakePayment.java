package com.eribank.pages;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import com.eribank.base.BaseEribank;
import com.eribank.utility.ReportUtility;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class MakePayment {
	private static WebElement element;
	private static Properties prop;
	private static InputStream mapLoginObj;
	private static HashMap<String, String> hmap;
//private HashMap<String, String> Loginmap;

public static void setObjectsToMap()throws IOException {
	element = null;
	prop = new Properties();
	mapLoginObj = BaseEribank.class.getResourceAsStream("/MakePayment.properties");
	hmap = new HashMap<String, String>();
	prop.load(mapLoginObj);
	hmap.putAll(prop.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));	
}    
    private static WebElement textPhone(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("phone"));
    	return element;
    }
    private static WebElement textName(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("name"));
    	return element;
    }
    private static WebElement textAmount(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("amount"));
    	return element;
    }
    private static WebElement selectCountry(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("country"));
    	return element;
    }
    private static WebElement buttonClickGermay(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
		new TouchAction((MobileDriver) driver).press(408, 1045).waitAction(Duration.ofMillis(2000)).moveTo(-108, 200).release().perform();
		new TouchAction((MobileDriver) driver).press(408, 1045).waitAction(Duration.ofMillis(2000)).moveTo(-187, 286).release().perform();
    	element = driver.findElementByXPath(hmap.get("selectGermany"));
    	return element;
    }
    private static WebElement buttonsendPayment(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("sendPaymentButton"));
    	return element;
    }
    private static WebElement buttonYes(AndroidDriver<AndroidElement> driver)throws IOException {
    	setObjectsToMap();
    	element = driver.findElementByXPath(hmap.get("yesPopup"));
    	return element;
    }
    
    public Object doMakePayment(AndroidDriver<AndroidElement> driver, String phone, String name, String amount)throws Exception {
		textPhone(driver).sendKeys(phone);
		textName(driver).sendKeys(name);
		textAmount(driver).sendKeys(amount);
		selectCountry(driver).click();
		buttonClickGermay(driver).click();
		ReportUtility.getInstance().catureScreenshot(driver,"Make Payment");
		buttonsendPayment(driver).click();
		ReportUtility.getInstance().catureScreenshot(driver,"Make Payment Pop up");
		buttonYes(driver).click();
		return BaseEribank.getObject("com.eribank.pages.Dashboard");
    }

}
