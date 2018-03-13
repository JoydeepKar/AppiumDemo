package com.salesforce.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseSalesforce {

    private static BaseSalesforce single_instance = null;
    private String reportDirectory;
    private String reportFormat;
    private String testName;
    protected AndroidDriver<AndroidElement> driver;
    //private WebElement element;
    DesiredCapabilities dc;

    // private constructor restricted to this class itself
    private BaseSalesforce(){
    	//single_instance = null;
    	reportDirectory = "reports";
    	reportFormat = "xml";
    	testName = "Untitled";
    	driver = null;
    	dc = new DesiredCapabilities();
    }
 
    // static method to create instance of Singleton class
    public static BaseSalesforce getInstance()
    {
        if (single_instance == null)
            single_instance = new BaseSalesforce();
 
        return single_instance;
    }
    
    public AndroidDriver<AndroidElement> getBase() throws MalformedURLException {
	  dc.setCapability("reportDirectory", reportDirectory);
	  dc.setCapability("reportFormat", reportFormat);
	  dc.setCapability("testName", testName);
	  dc.setCapability(MobileCapabilityType.UDID, "62210909GH106C408V1");
	  dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.salesforce.chatter");
	  dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Chatter");
	  driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
	  return driver;
    }
    
	public static Object getObject(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = Class.forName(className);
		Constructor<?> ctor = clazz.getConstructor();
		Object object = ctor.newInstance();
		return object;
	}

}