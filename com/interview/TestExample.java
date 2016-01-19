package com.interview;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestExample {

	WebDriver driver = null ;
	
	private ExtentReports extent = ReportManager.getInstance();
    private ExtentTest log;
    private Date curDate = new Date();
	SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
    
    @Before
	public void setup () {
		driver = Browser.getCurrentBrowser("firefox") ;				
	}
    
    @Test
	public void createRepeatingInvoice_success(){    	
		log = extent
	            .startTest("createRepeatingInvoice_success", "create Repeating Invoice") ;
		
		log.log(LogStatus.INFO, "given : username and password") ;
		LoginPage main = new LoginPage (driver);
		
		log.log(LogStatus.INFO, "when : users login the system ") ;
		XeroPage xeroPage = main.login(Constant.USERNAME, Constant.PWD);
		
		log.log(LogStatus.INFO, "and : users go to Sale page ") ;
		XeroSalesPage sales = xeroPage.toAccount().toSales();
		
		log.log(LogStatus.INFO, "and : users go to repeating invoice page ") ;
		NewRepeatingInvoicePage invoice = sales.toRepeatingInvoice() ;
		
		log.log(LogStatus.INFO, "when : users create a repeatng invoice and click on save button ") ;
		XeroInvoicePage invoicePage = invoice.repeatTransaction("3", "Month(s)").invoiceDate("12 Dec 2015").
		  dueDate("2", "of the following month").approve().
		   invoiceTo("may").reference("sale1").
		   addDescription(0).
		   description("hello").quantity("3").unitprice("100").account("260 - Other Revenue").end().
		   addDescription(1).
		   description("hello").quantity("6").unitprice("100").account("960 - Retained Earnings").end().
		   save() ;
		log.log(LogStatus.INFO, "then : verify a new invoice is created") ;
		List<Map<String,String>> rst = invoicePage.table() ;
		
		assertTrue(rst.size() != 0);
		log.log(LogStatus.PASS, "invoice result is not empty" ) ;
		
		Map<String,String> row = rst.get(rst.size() - 1) ;		
		assertTrue("may".equals(row.get("Name")));
		log.log(LogStatus.PASS, "invoice name is may" ) ;
		
		assertTrue("sale1".equals(row.get("Reference")));
		log.log(LogStatus.PASS, "invoice Reference is sale1" ) ;
				
	}
    
    @Test
   	public void createRepeatingInvoice_missing_fields_failed(){    	
   		log = extent
   	            .startTest("createRepeatingInvoice_missing_fields_failed", "create Repeating Invoice with out manatory fields") ;
   		
   		log.log(LogStatus.INFO, "given : username and password") ;
   		LoginPage main = new LoginPage (driver);
   		
   		log.log(LogStatus.INFO, "when : users login the system ") ;
   		XeroPage xeroPage = main.login(Constant.USERNAME, Constant.PWD);
   		
   		log.log(LogStatus.INFO, "and : users go to Sale page ") ;
   		XeroSalesPage sales = xeroPage.toAccount().toSales();
   		
   		log.log(LogStatus.INFO, "and : users go to repeating invoice page ") ;
   		NewRepeatingInvoicePage invoice = sales.toRepeatingInvoice() ;
   		
   		log.log(LogStatus.INFO, "when : users create a repeatng invoice and do not fill in all madatory fields and then click on save button") ;
   		List<WebElement> errors = invoice.repeatTransaction("3", "Month(s)").invoiceDate("12 Dec 2015").
   		  dueDate("2", "of the following month").approve().
   		   invoiceTo("may").reference("sale1").triggerError() ;
   		
   		log.log(LogStatus.INFO, "then : this is an error message in the UI") ;
   	    assertTrue(errors.size() == 1);
   			log.log(LogStatus.PASS, "we got an error in the UI" ) ;   		
   			
   	    assertTrue("Description cannot be empty.".equals(errors.get(0).getText().trim()));
   		log.log(LogStatus.PASS, "the error message is Description cannot be empty." ) ;

   	}
    
    @Test
    public void end_date_before_invoice_date_failed(){    	
		log = extent
	            .startTest("end_date_before_invoice_date_failed", "create Repeating Invoice with end date is before invoice date") ;
		
		log.log(LogStatus.INFO, "given : username and password") ;
		LoginPage main = new LoginPage (driver);
		
		log.log(LogStatus.INFO, "when : users login the system ") ;
		XeroPage xeroPage = main.login(Constant.USERNAME, Constant.PWD);
		
		log.log(LogStatus.INFO, "and : users go to Sale page ") ;
		XeroSalesPage sales = xeroPage.toAccount().toSales();
		
		log.log(LogStatus.INFO, "and : users go to repeating invoice page ") ;
		NewRepeatingInvoicePage invoice = sales.toRepeatingInvoice() ;
		
		log.log(LogStatus.INFO, "when : users create a repeatng invoice and click on save button ") ;
		
		//set end date 3 days before current date
		Date dateBefore = new Date(curDate.getTime() - 3 * 24 * 3600 * 1000L) ;
		
		log.log(LogStatus.INFO, "when : users create a repeatng invoice and the end date value is 3 days before current date then click on save button") ;
		List<WebElement> errors = invoice.repeatTransaction("3", "Month(s)").invoiceDate(format.format(curDate)).
		  dueDate("2", "of the following month").endDate(format.format(dateBefore)).
		  approve().
		   invoiceTo("may").reference("sale1").
		   addDescription(0).
		   description("hello").quantity("3").unitprice("100").account("260 - Other Revenue").end().triggerError() ;
		
			log.log(LogStatus.INFO, "then : this is an error message in the UI") ;
		    assertTrue(errors.size() == 1);
			log.log(LogStatus.PASS, "we got an error in the UI" ) ;   		
			
			assertTrue("End Date must be on or after the Invoice Date".equals(errors.get(0).getText().trim()));
			log.log(LogStatus.PASS, "the error message is End Date must be on or after the Invoice Date" ) ;

	}
    
    
   	@Test
   	public void createRepeatingInvoice_cancel(){    	
		log = extent
	            .startTest("createRepeatingInvoice_cancel", "cancel Repeating Invoice") ;
		
		log.log(LogStatus.INFO, "given : username and password") ;
		LoginPage main = new LoginPage (driver);
		
		log.log(LogStatus.INFO, "when : users login the system ") ;
		XeroPage xeroPage = main.login(Constant.USERNAME, Constant.PWD);
		
		log.log(LogStatus.INFO, "and : users go to Sale page ") ;
		XeroSalesPage sales = xeroPage.toAccount().toSales();
		
		log.log(LogStatus.INFO, "and : users go to repeating invoice page ") ;
		NewRepeatingInvoicePage invoice = sales.toRepeatingInvoice() ;
		
		log.log(LogStatus.INFO, "when : users create a repeatng invoice and click on cancel button ") ;
		XeroInvoicePage invoicePage = invoice.repeatTransaction("3", "Month(s)").invoiceDate("12 Dec 2015").
		  dueDate("2", "of the following month").approve().
		   invoiceTo("may").reference("unique").
		   addDescription(0).
		   description("hello").quantity("3").unitprice("100").account("260 - Other Revenue").end().
		   addDescription(1).
		   description("hello").quantity("6").unitprice("100").account("960 - Retained Earnings").end().
		   cancel() ;		
		
		log.log(LogStatus.INFO, "then : we are navigated to invoice page") ;		
		assertTrue(invoicePage != null);
		log.log(LogStatus.PASS, "we are at invoice page") ;
		
		
		log.log(LogStatus.INFO, "then : no new invoice is created ") ;
		List<Map<String,String>> rst = invoicePage.table() ;
		assertTrue(rst.size() == 0 || !"unique".equals(rst.get(rst.size() - 1).get("Reference")));
		log.log(LogStatus.PASS, "then : no new invoice is created ") ;
	}
   
    
    @After
    public void teardown () {	
		try{
			driver.quit();
		} catch (Exception ignore) {		
		}
		extent.endTest(log);
        extent.flush();
	}
}
