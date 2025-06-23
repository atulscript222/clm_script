package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage  extends basePage{
	
	WebDriver driver;
	
	public  LogInPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@placeholder='Enter your username']") WebElement enteruserName;
	@FindBy(xpath = "//input[@placeholder='Enter your password']") WebElement enterpassword;
	@FindBy(xpath = "//a[normalize-space()='Forgot Password?']") WebElement enterforgotPassword;
	@FindBy(xpath = "//a[normalize-space()='Forgot Password?']") WebElement enterforgotPasswordButton;
	@FindBy(xpath = "//span[normalize-space()='Login']") WebElement enterlogginButton;
	@FindBy(xpath = "//img[@alt='Expand menu']") WebElement expand;
	@FindBy(xpath = "//span[normalize-space()='Logout']") WebElement logout;
	@FindBy(xpath = "//button[normalize-space()='Logout']") WebElement finallogout;
	
	
	public void enteruserName (String Username) {
		enteruserName.clear();
		
		enteruserName.sendKeys(Username);	
	}
	
	
	public void enterpassword (String pwd) {
		enterpassword.clear();
		enterpassword.sendKeys(pwd);	
	}
	
	public void enterlogginButton () {
		
		enterlogginButton.click();	
	}
	
	public void logoutportal() {
		
		expand.click();
		logout.click();
		finallogout.click();
	}
}

