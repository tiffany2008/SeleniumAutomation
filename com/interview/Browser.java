package com.interview;


import java.awt.Toolkit;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {
	
	private static WebDriver browser = null ;
	
	public static synchronized WebDriver getCurrentBrowser (String type){
        if ("firefox".equals(type.toLowerCase())) {
        	browser = new FirefoxDriver ();
        } else if ("chrome".equals(type.toLowerCase())) {
        	browser = new ChromeDriver ();
        } else {
        	throw new NoSuchElementException ("browser doest not support");
        }
		config (browser);
		
		
		HashMap<String,WebDriver> map = new  HashMap <> ();
		map.put("firefox", new FirefoxDriver ()) ;
		map.put("chrome", new  ChromeDriver ()) ;
		map.get(type) ;
		return browser;
	}
		
	private static void config(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
        driver.manage().window().setPosition(new Point(0, 0));        
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        driver.manage().window().setSize(dim);
	}

}

