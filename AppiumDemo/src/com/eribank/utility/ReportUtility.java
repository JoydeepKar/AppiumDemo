package com.eribank.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ReportUtility {
	private static ReportUtility single_instance = null;
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static ExtentTest test;
	static File src;
	static String filename;
    // private constructor restricted to this class itself
    private ReportUtility() {
    	//do nothing
    }
 
    // static method to create instance of Singleton class
    public static ReportUtility getInstance() {
        if (single_instance == null)
            single_instance = new ReportUtility();
 
        return single_instance;
    }
    
    public void doReportSetup() {
 		htmlReporter = new ExtentHtmlReporter("Reports/"+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".html");
 		extent = new ExtentReports();
 		extent.attachReporter(htmlReporter);
 		htmlReporter.setAppendExisting(true);
    }
    public void catureScreenshot(AndroidDriver<AndroidElement> driver, String description, long millis)throws Exception{
    	Thread.sleep(millis);
	    src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	    FileUtils.copyFile(src, new File("Screens/"+filename+".png"));
	    test.pass(description, MediaEntityBuilder.createScreenCaptureFromPath("../Screens/"+filename+".png").build());
	    test.addScreenCaptureFromPath("../Screens/"+filename+".png");
    }
    public void startTest(String name, String description) {
	    test = extent.createTest(name, description);
	    test.assignAuthor("Joydeep Kar");
    }
    public void endSetup() {
    	extent.flush();
    }
    public void addComments(String comments) {
    	test.info(comments);
    }

}
