package com.opencart.testbase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        boolean runOnGrid = Boolean.parseBoolean(getProperty("useGrid"));

        if (runOnGrid) {
            driver = setupRemoteWebDriver(os, browser);
        } else {
            driver = setupLocalWebDriver(browser);
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

    private WebDriver setupLocalWebDriver (String browser) {
        getLogger().info("Setting up Local WebDriver");

        switch (browser.toLowerCase()){
            case "chrome": return new ChromeDriver();
            case "firefox": return new FirefoxDriver();
            case "edge": return new EdgeDriver();
            default:
                getLogger().error("Invalid browser name: "+ browser);
                throw new IllegalArgumentException("Invalid browser name: "+ browser);
        }
    }

    private WebDriver setupRemoteWebDriver (String os, String browser) throws MalformedURLException {
        getLogger().info("Setting up Remote WebDriver");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browser.toLowerCase()) {
            case "chrome":
                capabilities.setBrowserName("chrome");
                getLogger().info("Browser is set as: Chrome");
                break;
            case "edge":
                capabilities.setBrowserName("MicrosoftEdge");
                getLogger().info("Browser is set as: Edge");
                break;
            case "firefox":
                capabilities.setBrowserName("firefox");
                getLogger().info("Browser is set as: Firefox");
                break;
            case "safari":
                capabilities.setBrowserName("safari");
                getLogger().info("Browser is set as: Safari");
                break;
            default:
                getLogger().warn("Invalid browser specified: " + browser + "Defaulting to chrome");
                capabilities.setBrowserName("chrome");
        }

        switch (os.toLowerCase()) {
            case "windows":
                capabilities.setPlatform(Platform.WINDOWS);
                getLogger().info("Platform is set as: Windows");
                break;
            case "linux":
                capabilities.setPlatform(Platform.LINUX);
                getLogger().info("Platform is set as: Linux");
                break;
            case "mac":
                capabilities.setPlatform(Platform.MAC);
                getLogger().info("Platform is set as: Mac");
                break;
            default:
                getLogger().warn("Invalid OS specified, defaulting to ANY");
                capabilities.setPlatform(Platform.ANY);
        }

        String gridURL = getProperty("gridURL");
        if (gridURL == null || gridURL.isEmpty()) {
            gridURL = "http://localhost:4444/wd/hub";
        }

        getLogger().info("Connecting to Grid at: " + gridURL);
        getLogger().info("Browser: " + browser + ", OS: " + os);

        return new RemoteWebDriver(new URL(gridURL), capabilities);
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
