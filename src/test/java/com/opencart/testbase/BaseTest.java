package com.opencart.testbase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    private ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> tWait = new ThreadLocal<>();
    private ThreadLocal<Logger> tLogger = new ThreadLocal<>();
    private Properties properties;

    public void setDriver(WebDriver driver) {
        tDriver.set(driver);
    }

    public WebDriver getDriver() {
        WebDriver driver = tDriver.get();
        if (driver == null) {
            System.out.println("WARNING: WebDriver is null!");
            System.out.println("Current Thread: " + Thread.currentThread().getName());
            Thread.dumpStack();
        }
        return driver;
    }

    public void setWait(WebDriverWait wait) {
        tWait.set(wait);
    }

    public WebDriverWait getWait() {
        return tWait.get();
    }

    public void setLogger(Logger logger) {
        tLogger.set(logger);
    }

    public Logger getLogger() {
        return tLogger.get();
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

        Logger logger = LogManager.getLogger(this.getClass());
        setLogger(logger);

        getLogger().info("Setting up WebDriver...");
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
        getLogger().info("Browser launched and maximized.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        getLogger().info("Tearing down...");
        System.out.println("tearDown Thread: " + Thread.currentThread().getName());
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            getLogger().info("Browser closed.");
        }
        tDriver.remove();
        tWait.remove();
        tLogger.remove();
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
