package testCases;


import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import pageObjects.LogInPage;
import testBase.base;

public class Tc001_login extends base{
	
	@Test(groups = "sanity")
	public void loginTest() {
		
		logger.info("entering the portal");
		
		LogInPage login = new LogInPage(driver);
		login.enteruserName(p.getProperty("user_name"));
		login.enterpassword(p.getProperty("password"));
		login.enterlogginButton();
		
	}

	
		
	// random alphabets

	
}
