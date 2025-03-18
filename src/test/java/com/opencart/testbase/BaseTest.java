package com.opencart.testbase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    private ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> tWait = new ThreadLocal<>();
    public Logger logger;
    private Properties properties;

    public void setDriver(WebDriver driver) {
        tDriver.set(driver);
    }

    public WebDriver getDriver() {
        return tDriver.get();
    }

    public void setWait(WebDriverWait wait) {
        tWait.set(wait);
    }

    public WebDriverWait getWait() {
        return tWait.get();
    }

    public String getProperty (String key) {
        return properties.getProperty(key);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"os", "browser"})
    public void setup(String os, String browser) throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        properties = new Properties();
        properties.load(file);

        logger = LogManager.getLogger(this.getClass());

        logger.info("Setting up WebDriver...");
        WebDriver driver;
        switch (browser.toLowerCase()){
            case "chrome": driver = new ChromeDriver(); break;
            case "firefox": driver = new FirefoxDriver(); break;
            case "edge": driver = new EdgeDriver(); break;
            default: System.out.println("Invalid browser name!"); return;
        }
        setDriver(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        setWait(wait);

        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        getDriver().get(getProperty("appURL"));
        getDriver().manage().window().maximize();
        logger.info("Browser launched and maximized.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
        tDriver.remove();
        tWait.remove();
    }

    public String captureScreen (String testName) {
        try {
            String SCREENSHOT_PATH = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;
            File directory = new File(SCREENSHOT_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            WebDriver driver = getDriver();
            if (driver == null) {
                logger.error("Failed to capture screenshot: WebDriver instance is null");
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss-z").format(new Date());
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String targetFilePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + testName + "_" + timeStamp + ".png";

            FileUtils.copyFile(sourceFile, new File(targetFilePath));
            logger.info("Screenshot captured: " + targetFilePath);
            return targetFilePath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot" + e.getMessage());
            return "";
        }
    }

    public static String randomString(){
        String rString = RandomStringUtils.randomAlphabetic(5);
        return rString;
    }

    public static String randomNumber(){
        String rNumber = RandomStringUtils.randomNumeric(10);
        return rNumber;
    }

    public static String randomAlphaNumeric(){
        String rString = RandomStringUtils.randomAlphabetic(3);
        String rNumber = RandomStringUtils.randomNumeric(3);
        return (rString+rNumber);
    }
}
