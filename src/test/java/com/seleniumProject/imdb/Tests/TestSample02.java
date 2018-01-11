package com.seleniumProject.imdb.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

/**
 * This test is goes to imdb.com home page
 * and it checks searchBox suggestions
 * with given keyWord than tries the match them
 *
 */
public class TestSample02 {
	/* Extent - Report API elements */
	ExtentReports extent;

	ExtentHtmlReporter htmlReporter;

	ExtentTest logger;
	/*********************************/

	// Needed Pages for the test
	HomePage objHomePage;

	WebDriver driver;
	
	// providing data for all tests
	@DataProvider(name="TestData")
	public Object[][] getData(){
		
		return new Object[][] {
			{"Godfather","TestSample02_1"},
			{"Harry potter","TestSample02_2"},
			{"Star Wars","TestSample02_3"},
			{"Tom Hulce","TestSample02_4"},
		};
	}
	
	// test sample with parameters
	@Test(dataProvider="TestData")
	public void checkMovieNames(String keyWord, String reportName) {
		// assuming test is passed
		boolean testControl = true;
		
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
		htmlReporter.config().setReportName("Check Top Rated Movies");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		/*************************************/
		
		// creating driver with given browser and URL
		driver = CrossBrowserHandler.startBrowser("chrome", "http://www.imdb.com/");

		// creating logger
		logger = extent.createTest("Try To Find Suggestions");

		objHomePage = new HomePage(driver);
		
		// getting first 3 suggestions with given keyWord
		String[] movieList = objHomePage.getSuggestions(keyWord,3);
		
		for(int i = 0; i < movieList.length ; i++) {
			// check either on of them is found (TR or ENG)
			if(movieList[i].toLowerCase().contains(keyWord.toLowerCase())) {
				// keep logging if its matched
				logger.log(Status.PASS, MarkupHelper.createLabel(
						(i+1)+". found", ExtentColor.GREEN));
			}
			else {
				testControl = false;
				// catch error and log as fail
				logger.log(Status.FAIL, MarkupHelper.createLabel(
						(i+1)+". not found!", ExtentColor.RED));
			}
		}
		
		// cleanup
		extent.flush();
		driver.close();

		// check if the test is passed
		Assert.assertTrue(testControl);
		
	}



}
