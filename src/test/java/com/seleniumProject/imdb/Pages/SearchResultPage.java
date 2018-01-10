package com.seleniumProject.imdb.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 
 * This class is a description for result page
 * due to homePage's search box execution
 *
 */
public class SearchResultPage {
	WebDriver driver;

	@FindBy(xpath = "//*[@id=\"main\"]/div/div[2]/table/tbody/tr[1]/td[2]/a")	
	WebElement firstResultFromName;

	public SearchResultPage(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(driver, this);

	}
	
	/**
	 * This function clicks the first text-link from the list
	 * 
	 */
	public void clickFirstResultFromName() {
		
		firstResultFromName.click();
		
	}
	
	
}
