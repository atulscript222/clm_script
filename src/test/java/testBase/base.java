package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import pageObjects.LogInPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class base {

    public WebDriver driver;
    public Logger logger = LogManager.getLogger(this.getClass());
    public Properties p;

    @BeforeClass
    @Parameters({"os", "browser"})
    public void setup(String os, String browser) throws IOException {

        // Load properties
        FileReader file = new FileReader("C:\\Users\\admin\\eclipse-workspace\\123\\src\\test\\resources\\config.properties");
        p = new Properties();
        p.load(file);

        logger.info("🔧 Setting up browser for OS: {} and Browser: {}", os, browser);

        // Grid Setup
        if (p.getProperty("runtest").equalsIgnoreCase("remote")) {
            DesiredCapabilities desiredCap = new DesiredCapabilities();

            // Set platform
            switch (os.toLowerCase()) {
                case "windows":
                    desiredCap.setPlatform(Platform.WIN10);
                    break;
                case "mac":
                    desiredCap.setPlatform(Platform.MAC);
                    break;
                default:
                    logger.error("❌ Unsupported OS: " + os);
                    throw new IllegalArgumentException("OS not supported: " + os);
            }

            // Set browser
            switch (browser.toLowerCase()) {
                case "chrome":
                    desiredCap.setBrowserName("chrome");
                    break;
                case "edge":
                    desiredCap.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    desiredCap.setBrowserName("firefox");
                    break;
                default:
                    logger.error("❌ Unsupported browser: " + browser);
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }

            driver = new RemoteWebDriver(new URL("http://192.168.0.124:4444"), desiredCap);
        }

        // Local Setup
        else if (p.getProperty("runtest").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    logger.error("❌ Unsupported browser: " + browser);
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(p.getProperty("beta-clm_url"));
        logger.info("✅ Navigated to: " + p.getProperty("beta-clm_url"));
    }

    // 🔐 Reusable login method
    public void loginToCLM() {
        logger.info("🔐 Performing login to CLM portal");

        LogInPage login = new LogInPage(driver);
        login.enteruserName(p.getProperty("user_name"));
        login.enterpassword(p.getProperty("password"));
        login.enterlogginButton();

        logger.info("✅ Login successful with user: " + p.getProperty("user_name"));
    }

    @AfterClass
    public void teardown() {
        logger.info("🧹 Closing browser...");
        if (driver != null) {
            driver.quit();
        }
        logger.info("🛑 Browser closed.");
    }

    // 🔤 Generate random string
    public String randomAlphabets() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumbers() {
        return RandomStringUtils.randomNumeric(5);
    }

    public String betaClmUrl() {
        return "https://beta-clm.doqfy.in/login";
    }

    // 📸 Capture screenshot
    public String captureScreen(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String screenshotDir = "C:\\Users\\admin\\eclipse-workspace\\123\\screenShots";
        String filePath = screenshotDir + "\\" + testName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);
        FileUtils.copyFile(srcFile, destFile);

        logger.info("📸 Screenshot saved at: " + filePath);
        return filePath;
    }
}

