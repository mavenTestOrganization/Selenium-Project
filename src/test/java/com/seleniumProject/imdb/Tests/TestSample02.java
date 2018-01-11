package com.seleniumProject.imdb.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.seleniumProject.helper.CrossBrowserHandler;
import com.seleniumProject.imdb.Pages.HomePage;

public class TestSample02 {
	/* Extent - Report API elements */
	ExtentReports extent;

	ExtentHtmlReporter htmlReporter;

	ExtentTest logger;
	/*********************************/

	// Needed Pages for the test
	HomePage objHomePage;

	WebDriver driver;
	
	/**
	 * initialization
	 */
	@BeforeTest
	public void init() {
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") +"/test-output/"
						+ this.getClass().getSimpleName() +"_REPORT.html");
		extent = new ExtentReports ();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "borhanMorphy");
		extent.setSystemInfo("Environment", "Project Demo");
		extent.setSystemInfo("User Name", "Omer BORHAN");

		htmlReporter.config().setDocumentTitle("Selenium Project");
		htmlReporter.config().setReportName("Check Top Rated Movies");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);

		driver = CrossBrowserHandler.startBrowser("chrome", "http://www.imdb.com/");
	}
	
	@Test
	public void checkMovieNames(String keyWord) {
		// creating logger
		logger = extent.createTest("Try To Find Suggestions");

		objHomePage = new HomePage(driver);
		
		String[] movieList = objHomePage.getSuggestions(keyWord,3);
		
		for(int i = 0; i < movieList.length ; i++) {
			try {
				// check either on of them is found (TR or ENG)
				Assert.assertTrue(
						movieList[i].toLowerCase().contains(keyWord));
				// keep logging if its matched
				logger.log(Status.PASS, MarkupHelper.createLabel(
						(i+1)+". found", ExtentColor.GREEN));

			}catch(AssertionError e) {
				// catch error and log as fail
				logger.log(Status.FAIL, MarkupHelper.createLabel(
						(i+1)+". not found!", ExtentColor.RED));
			}

		}
	}
	
	@AfterTest
	public void cleanup() {
		extent.flush();
		driver.close();
	}
}
