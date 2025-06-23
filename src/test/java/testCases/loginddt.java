package testCases;

import org.testng.annotations.Test;

import pageObjects.LogInPage;
import testBase.base;
import utitlites.dataProvider;

public class loginddt extends base {

//	@Test(dataProvider = "logindata", dataProviderClass = dataProvider.class)//get data provider form different class
//	public void verifylogin(String user_name , String password) {
//		
//			
//			logger.info("entering the portal");
//			
//			LogInPage login = new LogInPage(driver);
//			login.enteruserName(user_name);
//			login.enterpassword(password);
//			login.enterlogginButton();
//			
//		}
	@Test(dataProvider = "logindatawithoutexcel", dataProviderClass = dataProvider.class , groups = "regression")
    public void verifylogin(String user_name, String password) {

        logger.info("Entering the portal with: " + user_name);

        LogInPage login = new LogInPage(driver);
        
        login.enteruserName(user_name);
        login.enterpassword(password);
        login.enterlogginButton();
        login.logoutportal();
        
        // Add login verification here if needed
    }
	}

