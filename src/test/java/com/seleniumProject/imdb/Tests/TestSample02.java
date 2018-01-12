package com.seleniumProject.imdb.Tests;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.seleniumProject.helper.CrossBrowserHandler;
import com.seleniumProject.helper.ExtentAPIReporter;
import com.seleniumProject.imdb.Pages.HomePage;

/**
 * This test is goes to imdb.com home page
 * and it checks searchBox suggestions
 * with given keyWord than tries the match them
 *
 */
public class TestSample02 {
	static final Logger logger = LogManager.getLogger(TestSample02.class);
	// Extent - Reporter object
	ExtentAPIReporter objReporter;
	
	// Needed Pages for the test
	HomePage objHomePage;

	WebDriver driver;
	
	// providing data for all tests
	@DataProvider(name="TestData")
	public Object[][] getData(){
		
		return new Object[][] {
			{"Godfather","checkMovieNames"},
			{"Harry potter","checkMovieNames"},
			{"Star Wars","checkMovieNames"},
			{"Tom Hulce","checkMovieNames"},
		};
	}
	
	// test sample with parameters
	@Test(dataProvider="TestData")
	public void checkMovieNames(String keyWord, String reportName) {
		
		// create reporter object
		objReporter = new ExtentAPIReporter(keyWord+"_"+reportName);
		
		logger.info("Opening Browser");
		// creating driver with given browser and URL
		driver = CrossBrowserHandler.startBrowser("chrome", "http://www.imdb.com/");
		
		// creating a test logger
		objReporter.setupLogger("Check Suggestions");

		objHomePage = new HomePage(driver);
		
		// getting first 3 suggestions with given keyWord
		String[] movieList = objHomePage.getSuggestions(keyWord,3);
		logger.trace("Checking suggestions");
		for(int i = 0; i < movieList.length ; i++) {
			// check either on of them is found (TR or ENG)
			if(movieList[i].toLowerCase().contains(keyWord.toLowerCase())) {
				// keep logging if its matched
				objReporter.LOG(Status.PASS, (i+1)+". found");
				logger.debug((i+1)+". found");
			}
			else {
				// log fail case
				objReporter.LOG(Status.ERROR, (i+1)+". not found!");
				logger.error((i+1)+". not found");
			}
		}
		
		logger.info("Closing browser");
		// cleanup
		objReporter.cleanup();
		driver.close();
		
		// check if the test is passed
		Assert.assertTrue(objReporter.isTestControl());
		
	}



}
