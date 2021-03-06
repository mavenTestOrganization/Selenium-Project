package com.seleniumProject.imdb.Tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.seleniumProject.helper.CrossBrowserHandler;
import com.seleniumProject.helper.ExtentAPIReporter;
import com.seleniumProject.imdb.Pages.HomePage;
import com.seleniumProject.imdb.Pages.TopRatedPage;

/**
 * This test goes to www.imdb.com home page and
 * clicks Top Rated Movies.
 * Than it tries to match given movies with the parameters
 *
 */
public class TestSample01 {
	
	static final Logger logger = LogManager.getLogger(TestSample01.class);
	// Extent - Reporter object
	ExtentAPIReporter objReporter;

	// Needed Pages for the test
	HomePage objHomePage;
	TopRatedPage objTopRatedPage;

	WebDriver driver;
	
	// Given movie list both TR and ENG
	String[] arrayEN = {"The Shawshank Redemption", "The Godfather",
			"The Godfather: Part II", "The Dark Knight", "Schindler's List"};

	String[] arrayTR = {"Esaretin Bedeli", "Baba", 
			"Baba 2", "Kara Sövalye", "Schindler'in Listesi"};

	/**
	 * initialization
	 */
	@BeforeTest
	public void init() {
		objReporter = new ExtentAPIReporter("movie_control");
		
		logger.info("Opening Browser");
		// creating driver with given browser and URL
		driver = CrossBrowserHandler.startBrowser("firefox", "http://www.imdb.com/");
	}

	@Test
	public void checkMovieNames() {
		
		// creating a test logger
		objReporter.setupLogger("movie check");
		
		// creating pages
		objHomePage = new HomePage(driver);
		logger.trace("Select Top Rated Movies");
		objHomePage.goToTopRatedMovies();

		objTopRatedPage = new TopRatedPage(driver);
		
		// getting top rated movies from the list with count of array length
		String[] movieList = objTopRatedPage.getTopRatedMovies(arrayTR.length);
		logger.debug("Checking List");
		for(int i = 0; i < movieList.length ; i++) {
			// check either on of them is found (TR or ENG)
			if(arrayEN[i].equalsIgnoreCase(movieList[i]) ||
					arrayTR[i].equalsIgnoreCase(movieList[i]) 
					) {
				logger.debug(arrayEN[i]+" is matched" );
				// keep logging if its matched
				objReporter.LOG(Status.PASS , arrayEN[i]+" is matched" );
			}
			else {
				logger.error(arrayEN[i]+" is not matched" );
				// test fail. Add log
				objReporter.LOG(Status.ERROR , arrayEN[i]+" is not matched!" );
			}
		}
		
		logger.info("Closing Browser");
		// check if test is passed
		Assert.assertTrue(objReporter.isTestControl());

	}

	@AfterMethod
	public void handleError(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE){
			
			objReporter.LOG(Status.FAIL , result.getName() 
					+ result.getThrowable() );

		}
		else if(result.getStatus() == ITestResult.SKIP){
			
			objReporter.LOG(Status.SKIP ,
					result.getName() + " - Test Case Skipped" );
			
		}

		// cleanup
		objReporter.cleanup();
		driver.close();
	}

}
