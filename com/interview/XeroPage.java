package com.interview;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class XeroPage extends PageObject{
	
	@FindBy(css = "a[id=Accounts]")
	private WebElement account ; 
	
	private By dropDown = By.cssSelector("ul.xn-h-menu-list a") ;

	public XeroPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	
	public XeroPage toAccount(){		
		account.click() ;
		WebElement parent = account.findElement(By.xpath("..")) ;
		nagivateTo("Sales", parent, dropDown);
		waitForExists("Xero | Sales | Demo",10) ;
		return this ;
	}
	
	public XeroSalesPage toSales(){				 
		return new XeroSalesPage(driver);
	}
	
}
