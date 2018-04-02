package com.fusion.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HCMUtility {
	private static WebDriver driver ;
	private static WebElement element;

	public static boolean retryingFindClick(By by, WebDriver driver) {
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 3) {
	        try {
	            driver.findElement(by).click();
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	    return result;
	}

	public static boolean retryingFindInput(By by, String value, WebDriver driver) {
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 4) {
	        try {
	            driver.findElement(by).sendKeys(value);
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	    return result;
	}
	
	public static void senseCursorState(WebDriver driver) throws InterruptedException {
			while(true) {
				if(driver.findElement(By.tagName("body")).getCssValue("cursor").equals("wait") || driver.findElement(By.tagName("body")).getCssValue("cursor").equals("progress")) {
					System.out.println("Waited for 6 secs as cursor was busy");
					Thread.sleep(6000);
				}else {
					System.out.println("cursor is not busy");
					break;
				}
		}
	}
	
	public static WebElement waitForObject(By by, WebDriver driver) {
		element = new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOfAllElements(driver.findElement(by))).get(0);
		System.out.println("wait for object");
		return element;
	}
	
	public static void inputUsingAction(By by, WebDriver driver, String value) {
		element = waitForObject(by, driver);
		new Actions(driver).moveToElement(element).click().sendKeys(element,value).build();
	}
	
/*	public int getRowCount(String filename, String sheetName) {
		try {
			fis = new FileInputStream(new File(filename));
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			Row rowkey = sheet.getRow(0);
			totanumberofcolumns = rowkey.getLastCellNum();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return sheet.getLastRowNum();
	}
*/

}
