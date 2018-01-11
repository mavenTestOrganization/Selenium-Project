package com.seleniumProject.imdb.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
/**
 * 
 * Page contents for www.imdb.com top rated page section
 *
 */
public class TopRatedPage {

	WebDriver driver;

	public TopRatedPage(WebDriver driver) {

		this.driver = driver;

		//This initElements method will create all WebElements

		PageFactory.initElements(driver, this);

	}
	/**
	 * This function returns given top rated movies
	 * using given count parameter
	 * 
	 * @param count
	 * 
	 * @return String array that collected from driver
	 */
	public String[] getTopRatedMovies(int count) {
		
		String[] tempList = new String[count];

		for(int i = 0 ; i < count ; i++){	
			tempList[i] = driver.findElement(By.xpath(
					"//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody/tr["+(i+1)+"]/td[2]/a"
					)).getText();
		}

		return tempList;

	}

}