package com.seleniumProject.imdb.Tests;

import com.seleniumProject.imdb.Pages.HomePage;
import com.seleniumProject.imdb.Pages.SearchResultPage;

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
import com.seleniumProject.helper.MD5ImageComparator;
import com.seleniumProject.imdb.Pages.ActorProfilePage;

/**
 * Go imdb homepage, Search given name,
 * Search only names, Select first result
 * Compare image hashes.  
 * 
 */
public class TestSample03 {
	/*Extent - Report API elements */
	ExtentReports extent;

	ExtentHtmlReporter htmlReporter;

	ExtentTest logger;
	/*********************************/

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
			{Robert_Downey_JR,"TestSample03_1"},
			{Tom_Hanks,"TestSample03_2"}
		};
	}

	// test sample with parameters
	@Test(dataProvider="TestImage")
	public void checkProfilePhoto(String URL, String reportName){
		// assuming test is passed
		boolean testControl = true;

		/* Extent - Reporter configurations */
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") +"/test-output/"
						+ reportName +"_REPORT.html");
		extent = new ExtentReports ();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "mchtctn");
		extent.setSystemInfo("Environment", "Project Demo");
		extent.setSystemInfo("User Name", "Mucahit Cetin");

		htmlReporter.config().setDocumentTitle("Selenium Project");
		htmlReporter.config().setReportName("Check Top Rated Movies");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		/**************************************/

		// creating driver with given browser and URL
		driver = CrossBrowserHandler.startBrowser("firefox", "http://www.imdb.com/");

		// creating logger
		logger = extent.createTest("Compare IMAGES");
		
		objHomePage = new HomePage(driver);

		objHomePage.setTextToSeachBox("Tom Hanks");
		objHomePage.setSearchReference();
		objHomePage.clickSearchButton();

		objSearchResultPage = new SearchResultPage(driver);

		objSearchResultPage.clickFirstResultFromName();

		objActorProfilePage = new ActorProfilePage(driver);
		
		if(MD5ImageComparator.compareIMAGE(
				URL,
				objActorProfilePage.getPhotoURL("name-poster") ) ) {
			// if picture is matched
			logger.log(Status.PASS, MarkupHelper.createLabel(
					"same", ExtentColor.GREEN));
		}
		else {
			testControl=false;
			//catch error and log as fail
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					"not same!", ExtentColor.RED));
		}

		// cleanup
		extent.flush();
		driver.close();

		// check if the test is passed
		Assert.assertTrue(testControl);
	}


}