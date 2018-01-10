/**
 * 
 */
package com.seleniumProject.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class provides multiple browser usage
 *
 */
public class CrossBrowserHandler {
	static WebDriver driver;

	/**
	 * This function runs the browser with given browser and URL
	 * @param browser as String
	 * @param URL as String
	 * @return WebDriver object
	 * 
	 */
	public static WebDriver startBrowser(String browser,String URL) {

		if( browser.equalsIgnoreCase("firefox") ) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir")+"src\\main\\drivers\\geckodriver.exe");

			driver = new FirefoxDriver();

		}
		else if( browser.equalsIgnoreCase("chrome") ) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")+"src\\main\\drivers\\chromedriver.exe");

			driver = new ChromeDriver();

		}
		else {
			// otherwise return null

			System.out.println("[SYSTEM] Given browser not found!");

			return null;

		}

		driver.manage().window().maximize();

		driver.get(URL);

		return driver;

	}
}
