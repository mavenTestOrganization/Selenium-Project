package com.seleniumProject.imdb.Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	@FindBy(xpath = "//*[@id=\"navbar-query\"]")
	WebElement searchBox;

	@FindBy(xpath = "//*[@id=\"navbar-submit-button\"]/div")
	WebElement searchButton;

	@FindBy(xpath = "//*[@id=\"quicksearch\"]")
	WebElement searchReference;

	@FindBy(xpath = "//*[@id=\"navMenu1\"]/div[2]/ul[1]/li[6]/a")
	WebElement showTopRatedMovies;

	@FindBys({
		@FindBy(xpath = "//*[@id=\\\"navbar-suggestionsearch\\\"]/a[0]/div"),
		@FindBy(xpath = "//*[@id=\\\"navbar-suggestionsearch\\\"]/a[1]/div"),
		@FindBy(xpath = "//*[@id=\\\"navbar-suggestionsearch\\\"]/a[2]/div")
	})
	List<WebElement> firstThreeSuggesstions;


	public HomePage(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(driver, this);

	}
	/**
	 * 
	 * @param
	 * 
	 * @return
	 */
	public void goToTopRatedMovies() {

		showTopRatedMovies.click();

	}


	public void setTextToSeachBox(String keyWords) {

		searchBox.clear();

		searchBox.sendKeys(keyWords);

	}

	/**
	 * This function types the given input to searchBox and
	 * finds the suggestions
	 * 
	 * @param keyWords
	 * 
	 * @return String array that collected from List<WebElements>
	 */
	public String[] getFirstThreeSuggesstions(String keyWords) {

		this.setTextToSeachBox(keyWords);
 
		String[] tempList = new String[firstThreeSuggesstions.size()];

		for(int i = 0 ; i < tempList.length ; i++)
			tempList[i] = firstThreeSuggesstions.get(i).getText();

		return tempList;
	}
	/**
	 *
	 * @param reference
	 */
	public void setSearchReference(String reference) {
		
		searchReference.sendKeys(reference);
		
	}

}
