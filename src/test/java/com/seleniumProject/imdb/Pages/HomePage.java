package com.seleniumProject.imdb.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page contents for www.imdb.com home page
 *
 */
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

	@FindBy(xpath = "//*[@id=\"navTitleMenu\"]/span")
	WebElement enableMenu;

	public HomePage(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(driver, this);

	}
	/**
	 * This function clicks the enable Menu and wait for
	 * visibility of Menu by given id than click the
	 * top rated movies
	 * @param
	 * 
	 * @return
	 */
	public void goToTopRatedMovies() {
		
		enableMenu.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		// wait until menu is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("navMenu1")));
		
		showTopRatedMovies.click();

	}

	public void setTextToSeachBox(String keyWords) {

		searchBox.clear();

		searchBox.sendKeys(keyWords);

	}

	/**
	 * This function types the given input to searchBox and
	 * finds the suggestions with range of given parameter
	 * @param keyWords
	 * @param count
	 * @return String[]
	 */
	public String[] getSuggestions(String keyWords,int range) {

		this.setTextToSeachBox(keyWords);
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		// wait until suggestion bar is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("navbar-suggestionsearch")));

		String[] tempList = new String[range];

		for(int i = 0 ; i < range ; i++) {
			tempList[i] = driver.findElement(By.xpath(
					"//*[@id=\"navbar-suggestionsearch\"]/a["+(i+1)+"]/div"
					)).getText();
		}
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
