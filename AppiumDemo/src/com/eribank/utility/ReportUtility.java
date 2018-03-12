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
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
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
    public void catureScreenshot(AndroidDriver<AndroidElement> driver)throws Exception{
	    File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	    FileUtils.copyFile(src, new File("Screens/"+filename+".png"));
	    test.pass("details", MediaEntityBuilder.createScreenCaptureFromPath("Screens/"+filename+".png").build());
    }
    public void startTest(String name, String description) {
	    test = extent.createTest("Make Payment", "This is to make a payment");
	    test.assignAuthor("Joydeep Kar");
    }
    public void endSetup() {
    	extent.flush();
    }
    public void addComments(String comments) {
    	test.info(comments);
    }

}