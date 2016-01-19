package com.interview;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends PageObject {

	/*
	 * css selectors
	 */	
		
	@FindBy(css = "input[id=email]")
	private WebElement user_Name ;
	
	@FindBy(css = "input[id=password]")
	private WebElement pwd ;
	
	@FindBy(css = "button[id=submitButton]")
	private WebElement login ;
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public XeroPage login(String userName, String password){
	    driver.get(Constant.URL) ;	
		doLogin (userName, password);
		return new XeroPage(driver);
	}
	
	private void doLogin(String userName, String password){
		 user_Name.sendKeys(userName) ;
		 pwd.sendKeys(password) ;
		 login.click() ;		 
		 waitForExists("Xero | Dashboard | Demo", 20);
		 System.out.println("test");
	}

}
