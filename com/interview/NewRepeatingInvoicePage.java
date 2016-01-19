package com.interview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class NewRepeatingInvoicePage extends XeroPage{

	
     @FindBy(css = "input[id=PeriodUnit]")
     private WebElement periodUnit ;
     
     @FindBy(css = "input[id=TimeUnit_value]")
     private WebElement timeUnit ;
     
     @FindBy(css = "input[id=StartDate]")
     private WebElement startDate ;
     
     @FindBy(css = "input[id=DueDateDay]")
     private WebElement dueDateDay ;
     
     @FindBy(css = "input[id=DueDateType_value]")
     private WebElement dueDateDay_value ;
     
     @FindBy(css = "input[id=EndDate]")
     private WebElement endDate ;
     
     @FindBy(css = "input[id=saveAsDraft]")
     private WebElement saveAs ;
     
     @FindBy(css = "input[id=saveAsAutoApproved]")
     private WebElement approve ;
     
     @FindBy(css = "input[id=saveAsAutoApprovedAndEmail]")
     private WebElement approve_sending ;
     
     @FindBy(css = "input[id^=PaidToName][type=text]")
     private WebElement invoiceTo ;
     
     @FindBy(css = "input[id^=Reference][type=text]")
     private WebElement reference ;
     
     @FindBy (css = "button[tabindex='252']")
     private WebElement save ;
     
     @FindBy (css = "a[tabindex='253']")
     private WebElement cancel ;
    
     @FindBy (css = "button[id=addNewLineItemButton]")
     private WebElement addNew ;
     
     
     private By error = By.cssSelector("div[id^=notify] div.message li") ;
     
     public NewRepeatingInvoicePage(WebDriver driver){
    	  super(driver);
    	  PageFactory.initElements(driver, this);
      }
      
      
      
      public NewRepeatingInvoicePage repeatTransaction(String day, String option){
    	  periodUnit.clear() ;    	  
    	  timeUnit.clear();
    	  periodUnit.sendKeys(day) ;
    	  timeUnit.sendKeys(option) ;    	  
    	  return this ;
      }
      
      public NewRepeatingInvoicePage invoiceDate(String date){
    	  startDate.sendKeys(date) ;
    	  return this ;
      }
      
      public NewRepeatingInvoicePage dueDate(String day, String option){
    	  dueDateDay.clear() ;
    	  dueDateDay_value.clear() ;
    	  dueDateDay.sendKeys(day) ;
    	  dueDateDay_value.sendKeys(option) ;
    	  return this ;
      }
      
      public NewRepeatingInvoicePage endDate(String date){
    	  endDate.sendKeys(date) ;
    	  return this ;
      }
      
      public NewRepeatingInvoicePage saveAsDraft(){
    	  return this ;
      }
      
      public NewRepeatingInvoicePage approve(){
    	  approve.click() ;
    	  return this ;
      }
  
      public NewRepeatingInvoicePage approveForSending(){
    	  return this ;
      }

      public NewRepeatingInvoicePage invoiceTo(String to){
    	  invoiceTo.sendKeys(to) ;
    	  return this ;
      }
      
      public NewRepeatingInvoicePage reference(String ref) {
    	  this.reference.sendKeys(ref);
    	  return this ;
      }
      
      public XeroInvoicePage save(){
    	  save.click() ;    	  
    	  waitForExists("Xero | Invoices | Demo",10) ;  		  
    	  return new XeroInvoicePage(driver) ;
      }
      
      public XeroInvoicePage cancel(){
    	  cancel.click() ;
    	  waitForExists("Xero | Invoices | Demo",10) ;  		  
    	  return new XeroInvoicePage(driver) ;
      }
      
      public List<WebElement> triggerError(){    	  	
    	  	save.click() ;  
    		pollList(error) ;
    		List<WebElement> errors = driver.findElements(error) ;
    	  return errors ;
      }
      
      public Table addDescription(int index){
    	  int c = driver.findElements(By.cssSelector("table.x-grid3-row-table")).size() ;
    	  if (index > c - 1) {
    		  addNew.click() ;
    	  }    	  
    	  return new Table(index);
      }
      
      
      
      class Table {    	  
    	  private List<WebElement> all ;
    	  private Map<String,Integer> headerMap = new HashMap<>() ;
    	  private int index ;    	  
    	  private By desp = By.cssSelector("div[id=invoice] textarea[id]") ;
    	  private By qty = By.cssSelector("div[id=invoice] input[id]") ;
    	  private By unitPrice = By.cssSelector("div[id=invoice] input[id=ext-comp-1005]") ;
    	  private By account_btn = By.cssSelector("div[id=invoice] img[id]") ;
    	  private By accountItem = By.cssSelector("div.x-combo-list-item") ;
    	  private By row = By.cssSelector(" tr td[class]") ;   
    	  private By header = By.cssSelector("div[id=invoice] table thead tr td") ;
    	  private By table = By.cssSelector("table.x-grid3-row-table") ;
     	  public Table (int index){
    		  this.index = index ;
    		  loadingHeader();
    		  all = getTables().get(index).findElements(row) ;    		      		 
    	  }
    	  
    	  public Table description(String text){
    		  safeclick(headerMap.get("Description"));
    		  driver.findElement(desp).sendKeys(text);
    		  return this ;
    	  }
    	  
    	  public Table quantity(String text){    		  
    		  safeclick(headerMap.get("Qty"));
    		  driver.findElement(qty).sendKeys(text);    		      		 
    		  return this;
    	  }
    	  
    	  public Table unitprice(String text){
    		  safeclick(headerMap.get("Unit Price"));
    		  driver.findElement(unitPrice).sendKeys(text);    		  
    		  return this;
    	  }
    	  
    	  public Table account(String option) {    		
    		safeclick(headerMap.get("Account"));       		
    		driver.findElement(account_btn).click() ;
    		pollList(accountItem); 	  			
  			for (WebElement ele : driver.findElements(accountItem)) {
  				if(option.equals(ele.getText())) ele.click() ;
  			}
    		return this ;
    	  }
    	  
    	  private void safeclick(int index){
    		  boolean state = true ;
    		  int count = 0 ;
    		  while (count < 10 && state) {    			  
    			  try{
    				  all.get(index).click() ;
    				  state = false ;
    			  } catch (StaleElementReferenceException  e) {    	    				 
    				  all = getTables().get(this.index).findElements(row) ;
    				  //reload header map
    				  loadingHeader();
    				  count++;
    			  }
    		  }
    	  }
    	  
    	  private List<WebElement> getTables(){
    		  return driver.
        			  findElements(table); 
    	  }
    	  
    	  public NewRepeatingInvoicePage end(){
    		  return NewRepeatingInvoicePage.this;
    	  }
     
    	 
    	  
    	  
    	  private void loadingHeader(){
    		  List<WebElement> headers = driver.findElements(header) ;
    		  for (int i = 0 ; i < headers.size() ; ++i) {
    			  String txt = headers.get(i).getText().trim() ;
    			  if (!"".equals(txt)) {
    				  headerMap.put(txt, i);
    			  }
    		  }    	    		  
    	  }
      
      }
      

}
