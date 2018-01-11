/**
 * 
 */
package com.seleniumProject.helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * This class provides Extent-API 
 * Reporter elements with ease
 * 
 */
public class ExtentAPIReporter {
	/* Extent - Report API elements */
	private ExtentReports extent;

	private ExtentHtmlReporter htmlReporter;

	private ExtentTest logger;
	/*********************************/

	// test controller (passed by default)
	private boolean testControl = true;

	public ExtentAPIReporter(String reportName) {
		init(reportName);
	}

	private void init(String reportName) {

		/* Extent - Reporter configurations */
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") +"/test-output/"
						+ reportName +"_REPORT.html");
		extent = new ExtentReports ();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "borhanMorphy");
		extent.setSystemInfo("Environment", "Project Demo");
		extent.setSystemInfo("User Name", "Omer BORHAN");

		htmlReporter.config().setDocumentTitle("Selenium Project");
		htmlReporter.config().setReportName("Check Suggestions");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		/*************************************/
	}
	
	// create a logger
	public void setupLogger(String testDescription) {

		logger = extent.createTest(testDescription);

	}
	
	// put a log to the logger
	public void LOG(Status logStatus,String logMessage) {
		ExtentColor logColor;
		switch(logStatus) {
		case PASS: logColor = ExtentColor.GREEN; break;
		
		case FAIL: logColor = ExtentColor.RED; setTestControl(false); break; // test fail
		
		case INFO: logColor = ExtentColor.WHITE; break;
		
		case DEBUG: logColor = ExtentColor.BLUE; break;
		
		case ERROR: logColor = ExtentColor.PINK; setTestControl(false); break;
		
		case FATAL: logColor = ExtentColor.TEAL; setTestControl(false); break; // test fail
		
		case SKIP: logColor = ExtentColor.GREY; break;
		
		case WARNING: logColor = ExtentColor.YELLOW; break;
		
		default: logColor = ExtentColor.WHITE; break;
		
		}
		
		logger.log(logStatus, 
				MarkupHelper.createLabel(logMessage, logColor));
		
	}
	
	// clean the reporter
	public void cleanup() {
		if(!this.isTestControl())
			this.LOG(Status.FAIL, "test failed");
		extent.flush();
	}

	/* set/get methods for testControl */
	public boolean isTestControl() {
		return testControl;
	}

	public void setTestControl(boolean testControl) {
		this.testControl = testControl;
	}




}
