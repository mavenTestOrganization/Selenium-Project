package com.seleniumProject.imdb.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seleniumProject.helper.CrossBrowserHandler;
import com.seleniumProject.imdb.Pages.HomePage;
import com.seleniumProject.imdb.Pages.TopRatedPage;

public class TestSample01 {
	
	WebDriver driver;
	
	HomePage objHomePage;
	TopRatedPage objTopRatedPage;
	/*String[] arr = {"The Shawshank Redemption", "The Godfather", "The Godfather: Part II",
			"The Dark Knight", "Schindler's List"};
	*/
	String[] arr = {"Esaretin Bedeli", "Baba", "Baba 2", "Kara Sövalye", "Schindler'in Listesi"};
	
	@BeforeTest
	public void init() {
		
		driver = CrossBrowserHandler.startBrowser("firefox", "http://www.imdb.com/");
		
	}
	
	@Test
	public void checkMovieNames() {
				
		objHomePage = new HomePage(driver);
		objHomePage.goToTopRatedMovies();
		
		objTopRatedPage = new TopRatedPage(driver);
		String[] mName = objTopRatedPage.getTopFiveRatedMovies();
		
		for(int i = 0; i < mName.length ; i++)
			Assert.assertTrue(mName[i].equalsIgnoreCase(arr[i]));
		
		//add log
	}
	
	@AfterTest
	public void cleanUp() {
		driver.close();
	}
}
