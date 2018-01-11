package com.seleniumProject.imdb.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
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
import com.seleniumProject.imdb.Pages.TopRatedPage;

/**
 * This test goes to www.imdb.com home page and
 * clicks Top Rated Movies.
 * Than it tries to match given movies with the parameters
 *
 */
public class TestSample01 {
	/* Extent - Report API elements */
	ExtentReports extent;

	ExtentHtmlReporter htmlReporter;

	ExtentTest logger;
	/*********************************/

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
		/* Extent - Reporter configurations */
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
		/************************************/
		
		// creating driver with given browser and URL
		driver = CrossBrowserHandler.startBrowser("chrome", "http://www.imdb.com/");
	}

	@Test
	public void checkMovieNames() {
		// assuming test is passed
		boolean testControl = true;

		// creating logger
		logger = extent.createTest("Try To Match Given Movies");

		objHomePage = new HomePage(driver);

		objHomePage.goToTopRatedMovies();

		objTopRatedPage = new TopRatedPage(driver);
		
		// getting top rated movies from the list with count of array length
		String[] movieList = objTopRatedPage.getTopRatedMovies(arrayTR.length);

		for(int i = 0; i < movieList.length ; i++) {
			// check either on of them is found (TR or ENG)
			if(arrayEN[i].equalsIgnoreCase(movieList[i]) ||
					arrayTR[i].equalsIgnoreCase(movieList[i]) 
					) {
				// keep logging if its matched
				logger.log(Status.PASS, MarkupHelper.createLabel(
						arrayEN[i]+" is matched", ExtentColor.GREEN));

			}
			else {
				testControl = false;
				// catch error and log as fail
				logger.log(Status.FAIL, MarkupHelper.createLabel(
						arrayEN[i]+" is not matched!", ExtentColor.RED));

			}
		}

		// check if test is passed
		Assert.assertTrue(testControl);

	}

	@AfterMethod
	public void handleError(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE){

			logger.log(Status.FAIL, MarkupHelper.createLabel(
					result.getName() + " - Test Case Failed - "+result.getThrowable(), ExtentColor.RED));

		}
		else if(result.getStatus() == ITestResult.SKIP){

			logger.log(Status.SKIP, MarkupHelper.createLabel
					(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));	

		}

		// cleanup
		extent.flush();
		driver.close();
	}

}
