package com.seleniumProject.imdb.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class ActorProfilePage {
	
	WebDriver driver;

	
	public ActorProfilePage(WebDriver driver) {
		
		this.driver = driver;
		
		//This initElements method will create all WebElements
		
		PageFactory.initElements(driver, this);
	}
	
	public String getPhotoURL(String id){
		return driver.findElement(By.id(id)).getAttribute("src");
	}


}
