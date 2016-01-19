package com.interview;



import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class PageObject {
	
	 protected WebDriver driver = null ;	
	 
	 public PageObject (WebDriver driver){
		 this.driver = driver ;
	 }
	 
	 public void waitForExists(final String title, long time){
		 WebDriverWait waiter = new WebDriverWait(driver, time);
			waiter.until(new Predicate<WebDriver>(){
				@Override
				public boolean apply(WebDriver driver) {					 
					return at(title); 
				}			
			});		
	 }
	 
	 public boolean at (String title){
		 for (String handle : driver.getWindowHandles()) {			 
			 driver.switchTo().window(handle) ;
			 if (title.equals(driver.getTitle())) {
				 return true ;
			 }
		 }
		 return false ;
	 }
	 
	 
	 
	 protected WebElement getObj(By by){
		return driver.findElement(by) ; 
	 }

	 //search under parent
	 protected void nagivateTo(String item, WebElement parent, By by){
		 for (WebElement link : parent.findElements(by)){									    
			    try{
			    	if (item.equals(link.getText())) link.click() ;			    	
			    } catch(StaleElementReferenceException ignore){			    	
			    }
		 }	 
	 }
	 
	 //search from top
	 protected void nagivateTo(final String item, By by){	
		 pollList(by) ;
		 for (WebElement link : driver.findElements(by)){						
			    try{
			    	if (item.equals(link.getText())) link.click() ;
			    } catch(StaleElementReferenceException ignore){			    	
			    }
		 }	 		
	 }
	 
	 protected void pollList(final By by){
		 WebDriverWait waiter = new WebDriverWait(driver, 10);
			waiter.until(new Predicate<WebDriver>(){
				@Override
				public boolean apply(WebDriver driver) {		
					return driver.findElements(by).size() > 0 ;
				}			
			});	
	 }
	 
	 
}
