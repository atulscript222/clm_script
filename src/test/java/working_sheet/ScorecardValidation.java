package working_sheet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScorecardValidation {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://beta-clm.doqfy.in");
            driver.manage().window().maximize();

            // Login
            driver.findElement(By.xpath("//input[@placeholder='Enter your username']")).sendKeys("atul@doqfy.in");
            driver.findElement(By.xpath("//input[@placeholder='Enter your password']")).sendKeys("Outlook@123");
            driver.findElement(By.xpath("//span[normalize-space()='Login']")).click();

            // Wait for Draft element to be clickable
            WebElement draftElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[@class='status-count count-draft']")));

            // ✅ Get text BEFORE clicking to avoid stale element issue
            String draftScorecardCount = draftElement.getText().trim();
            System.out.println("🔢 Draft Scorecard Count: " + draftScorecardCount);

            // Click on Draft to navigate
            draftElement.click();

            // Wait for contract list to load
            WebElement listingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class, 'list-item-info')]//span[last()]")));

            String contractsListingCount = listingElement.getText().trim();
            System.out.println("📋 Contracts Listing Count: " + contractsListingCount);

            // Compare
            if (draftScorecardCount.equals(contractsListingCount)) {
                System.out.println("✅ Test Passed: Both counts match.");
            } else {
                System.out.println("❌ Test Failed: Counts do not match.");
            }

        } catch (Exception e) {
            System.out.println("🚨 Exception occurred during test execution:");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
