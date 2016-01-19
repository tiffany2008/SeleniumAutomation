package com.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class XeroInvoicePage extends XeroPage{

	private By table = By.cssSelector("table tr[id]") ;
	
	public XeroInvoicePage(WebDriver driver) {
		super(driver);		
	}
	
    private HashMap<Integer,String> column = new HashMap<Integer,String>() {
    	{
    	  	put(1,"Name");
    	  	put(2,"Reference");
    	  	put(3,"Amount");
    	  	put(4,"Repeates");
    	  	put(5,"NextInvoiceDate");
    	  	put(6,"EndDate");
    	  	put(7,"InvoiceWillBe");
    	}
    } ;
    
	
	public List<Map<String,String>> table(){
		List<WebElement> rows = driver.findElements(table) ;
		List<Map<String,String>> rst = new ArrayList<> (); 
		for (WebElement e : rows) {
			List<WebElement> cols = e.findElements(By.tagName("td")) ;
			HashMap<String,String> cur = new HashMap<> ();
			for (int i = 1 ; i < cols.size() - 1 ; ++i) {				
				cur.put(this.column.get(i), cols.get(i).getText().trim()) ;
			}
			rst.add(cur) ;
		}
		return rst ;
	}
	
	
}
