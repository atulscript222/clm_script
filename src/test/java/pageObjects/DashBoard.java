package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashBoard extends basePage {

	public DashBoard(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class='status-count count-draft']")
	WebElement draftscorecard;

	@FindBy(xpath = "//p[contains(@class, 'list-item-info')]//span[last()]")
	WebElement contracts_listing_count;

	public String getDraftScorecardCount() {
		return draftscorecard.getText().trim();
	}

	public void clickDraftStatus() {
		draftscorecard.click();
	}

	public String getContractsListingCount() {
		return contracts_listing_count.getText().trim();
	}
}
