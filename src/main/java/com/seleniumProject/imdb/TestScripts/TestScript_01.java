package com.seleniumProject.imdb.TestScripts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

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
 * This test script goes to www.imdb.com home page and
 * clicks Top Rated Movies.
 * Than it tries to match given movies with the list
 *
 */
public class TestScript_01 {
	/* Extent - Report API elements */
	ExtentReports extent;

	ExtentHtmlReporter htmlReporter;

	ExtentTest logger;
	/*********************************/

	// Needed Pages for the testScript
	HomePage objHomePage;

	TopRatedPage objTopRatedPage;

	WebDriver driver;

	// Given movie list both TR and ENG
	String[] arrayEN,arrayTR;
	
	/* 						Script variables 							*/
	public String browser = "firefox", htmlReportName = "TestScript_01",
			hostName = "borhanMorphy",environment = "Personal Demo",
			userName = "Omer BORHAN", htmlDocTitle ="Selenium Project",
			reportName = "TOP RATED MOVIE CONTROL";
	/*********************************************************************/
	
	/**
	 * @param browser
	 */
	public TestScript_01(String browser) {
		this.browser = browser;
	}
	
	public TestScript_01() {}
	
	/**
	 * Before calling this method user must
	 * setup script variables if he/she wanted to
	 * do, otherwise test will use default values
	 * 
	 */
	public void init() {
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") +"/test-output/"
						+ htmlReportName +"_REPORT.html");
		extent = new ExtentReports ();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", hostName);
		extent.setSystemInfo("Environment", environment);
		extent.setSystemInfo("User Name", userName);

		htmlReporter.config().setDocumentTitle("Selenium Project");
		htmlReporter.config().setReportName(reportName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);

		driver = CrossBrowserHandler.startBrowser(browser, "http://www.imdb.com/");
	}

	public void checkMovieNames(String[] arrayEN,String[] arrayTR) {
		// creating logger
		logger = extent.createTest("Try To Match Given Movies");

		objHomePage = new HomePage(driver);

		objHomePage.goToTopRatedMovies();

		objTopRatedPage = new TopRatedPage(driver);

		String[] movieList = objTopRatedPage.getTopRatedMovies(arrayTR.length);

		for(int i = 0; i < movieList.length ; i++) {
			try {
				// check either on of them is found (TR or ENG)
				Assert.assertTrue(
						( arrayEN[i].equalsIgnoreCase(movieList[i])
						  ||
						  arrayTR[i].equalsIgnoreCase(movieList[i]) )
						);
				// keep logging if its matched
				logger.log(Status.PASS, MarkupHelper.createLabel(
						arrayEN[i]+" is matched", ExtentColor.GREEN));

			}catch(AssertionError e) {
				// catch error and log as fail
				logger.log(Status.FAIL, MarkupHelper.createLabel(
						arrayEN[i]+" is not matched!", ExtentColor.RED));
			}

		}
	}

	public void cleanup(boolean closeBrowser) {
		extent.flush();
		if(closeBrowser)
			driver.close();
	}










}
