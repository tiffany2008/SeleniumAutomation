package com.interview;

import com.relevantcodes.extentreports.ExtentReports;

public class ReportManager {
	
	private static ExtentReports extent;
	private static String REPORT = "./testReport.html" ;
	
	public static synchronized ExtentReports getInstance (){
		
		if (extent == null) {
			extent = new ExtentReports(REPORT, true);
			extent.config()
             .documentTitle("Automation Report")
             .reportName("Regression")
             .reportHeadline("");
			extent
             .addSystemInfo("Selenium Version", "2.46")
             .addSystemInfo("Environment", "QA");
		}		
		return extent ;
	}

}
