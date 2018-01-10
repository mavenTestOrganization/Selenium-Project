package com.seleniumProject.imdb.Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class TopRatedPage {

	WebDriver driver;

	@FindBys({
		@FindBy(xpath = "//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[1]/td[2]/a"),
		@FindBy(xpath = "//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[2]/td[2]/a"),
		@FindBy(xpath = "//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[3]/td[2]/a"),
		@FindBy(xpath = "//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[4]/td[2]/a"),
		@FindBy(xpath = "//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr[5]/td[2]/a")
	})
	List<WebElement> topFiveRatedMovies;
	
	public TopRatedPage(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(driver, this);

	}
	/**
	 * This function holds movie names. 
	 * 
	 * @param
	 * 
	 * @return String array that collected from List<WebElement>
	 */
	public String[] getTopFiveRatedMovies() {
		
		String[] myList = new String[topFiveRatedMovies.size()];

		for(int i = 0 ; i < myList.length ; i++)
			myList[i] = topFiveRatedMovies.get(i).getText();

		return myList;
		
	}
	
}