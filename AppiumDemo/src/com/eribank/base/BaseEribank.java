package com.eribank.base;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseEribank {

    private static BaseEribank single_instance = null;
    private String reportDirectory;
    private String reportFormat;
    private String testName;
    protected AndroidDriver<AndroidElement> driver;
    //private WebElement element;
    DesiredCapabilities dc;

    // private constructor restricted to this class itself
    private BaseEribank(){
    	//single_instance = null;
    	reportDirectory = "reports";
    	reportFormat = "xml";
    	testName = "Untitled";
    	driver = null;
    	dc = new DesiredCapabilities();
    }
 
    // static method to create instance of Singleton class
    public static BaseEribank getInstance()
    {
        if (single_instance == null)
            single_instance = new BaseEribank();
 
        return single_instance;
    }
    
    public AndroidDriver<AndroidElement> getBase() throws MalformedURLException {
	  dc.setCapability("reportDirectory", reportDirectory);
	  dc.setCapability("reportFormat", reportFormat);
	  dc.setCapability("testName", testName);
	  dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
	  dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
	  dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
	  driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
	  return driver;
    }
}