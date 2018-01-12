package com.seleniumProject.imdb.Tests;

import com.seleniumProject.imdb.Pages.HomePage;
import com.seleniumProject.imdb.Pages.SearchResultPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.seleniumProject.helper.CrossBrowserHandler;
import com.seleniumProject.helper.ExtentAPIReporter;
import com.seleniumProject.helper.MD5ImageComparator;
import com.seleniumProject.imdb.Pages.ActorProfilePage;

/**
 * Go imdb.com home page, Search given name,
 * Search only names, Select first result
 * Compare image hashes.  
 * 
 */
public class TestSample03 {
	
	static final Logger logger = LogManager.getLogger(TestSample03.class);
	// Extent - Reporter1 object
	ExtentAPIReporter objReporter;

	// Needed Pages for the test
	HomePage objHomePage;
	SearchResultPage objSearchResultPage;
	ActorProfilePage objActorProfilePage;

	WebDriver driver;

	// providing data for all tests
	@DataProvider (name="TestImage")
	public Object[][] getImage(){
		String Tom_Hanks = "https://images-na."
				+ "ssl-images-amazon.com/images/M/MV5BMTQ2MjMwNDA3Nl5BMl5BanBnXkF"
				+ "tZTcwMTA2NDY3NQ@@._V1_UY317_CR2,0,214,317_AL_.jpg";
		
		String Robert_Downey_JR = "https://images-na"
				+ ".ssl-images-amazon.com/images/M/MV5BNzg1MTUyNDYx"
				+ "OF5BMl5BanBnXkFtZTgwNTQ4MTE2MjE@._V1_UX214_CR0,0,214,317_AL_.jpg";
		
		return new Object[][] {
			{Robert_Downey_JR,"checkProfilePhoto_1","Tom Hanks"},
			{Tom_Hanks,"checkProfilePhoto_2","Tom Hanks"}
		};
	}

	// test sample with parameters
	@Test(dataProvider="TestImage")
	public void checkProfilePhoto(String URL, String reportName, String searchKey){
		
		// create reporter object
		objReporter = new ExtentAPIReporter(searchKey+"_"+reportName);

		// creating driver with given browser and URL
		logger.info("Opening browser");
		driver = CrossBrowserHandler.startBrowser("chrome", "http://www.imdb.com/");

		// creating logger
		objReporter.setupLogger("Compare Images");
		
		objHomePage = new HomePage(driver);

		objHomePage.setTextToSeachBox(searchKey);
		objHomePage.setSearchReference();
		objHomePage.clickSearchButton();

		objSearchResultPage = new SearchResultPage(driver);
		logger.trace("Select First Result");
		objSearchResultPage.clickFirstResultFromName();

		objActorProfilePage = new ActorProfilePage(driver);
		
		logger.trace("Comparing Image");
		if(MD5ImageComparator.compareIMAGE(
				URL,
				objActorProfilePage.getPhotoURL("name-poster") ) ) {
			// if pictures are matched
			objReporter.LOG(Status.PASS, "same" );
			logger.debug("Images are matched");
		}
		else {
			objReporter.LOG(Status.ERROR, "not same!");
			logger.error("Images are not matched!");
		}

		// cleanup
		objReporter.cleanup();
		driver.close();
		logger.info("Closing browser");
		// check if the test is passed
		Assert.assertTrue(objReporter.isTestControl());
	}


}