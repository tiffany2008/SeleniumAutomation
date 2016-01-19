package com.interview;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class XeroSalesPage extends XeroPage{

	@FindBy(css = "span.sq-new-item-list")
	private WebElement newItem ;
	
	private By dropDown = By.cssSelector("ul[id^=ext-gen] li a") ;
	
	public XeroSalesPage(WebDriver driver) {
		super(driver);
	}

	
	public NewRepeatingInvoicePage toRepeatingInvoice(){		
		newItem.click();		
		nagivateTo("Repeating invoice",dropDown) ;		
		waitForExists("Xero | New Repeating Invoice | Demo",10) ;		
		return new NewRepeatingInvoicePage(driver);
	}
		
}
