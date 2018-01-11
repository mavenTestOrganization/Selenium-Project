package com.seleniumProject.imdb.Tests;

import org.testng.annotations.Test;
import com.seleniumProject.imdb.TestScripts.TestScript_01;


public class TestSample01 {
	
	@Test
	public void checkMovieNames() {
		// Given movie list both TR and ENG
		String[] arrayEN = {"The Shawshank Redemption", "The Godfather",
				"The Godfather: Part II", "The Dark Knight", "Schindler's List"};

		String[] arrayTR = {"Esaretin Bedeli", "Baba", 
				"Baba 2", "Kara Sövalye", "Schindler'in Listesi"};
		
		TestScript_01 testSample01_1 = new TestScript_01("chrome");
		
		testSample01_1.init();
		
		
		testSample01_1.checkMovieNames(arrayEN,arrayTR);
		
		testSample01_1.cleanup(true);
	}

}
