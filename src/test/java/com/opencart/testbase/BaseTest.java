package com.opencart.testbase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public Logger logger;
    public Properties properties;

    @BeforeClass
//    @Parameters({"os", "browser"})
    public void setup() throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        properties = new Properties();
        properties.load(file);

        logger = LogManager.getLogger(this.getClass());

        logger.info("Setting up WebDriver...");
//        switch (browser.toLowerCase()){
//            case "chrome": driver = new ChromeDriver(); break;
//            case "firefox": driver = new FirefoxDriver(); break;
//            case "edge": driver = new EdgeDriver(); break;
//            default: System.out.println("Invalid browser name!"); return;
//        }

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(properties.getProperty("appURL"));
        driver.manage().window().maximize();
        logger.info("Browser launched and maximized.");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
        logger.info("Browser closed.");
    }

    public String randomString(){
        String rString = RandomStringUtils.randomAlphabetic(5);
        return rString;
    }

    public String randomNumber(){
        String rNumber = RandomStringUtils.randomNumeric(10);
        return rNumber;
    }

    public String randomAlphaNumeric(){
        String rString = RandomStringUtils.randomAlphabetic(3);
        String rNumber = RandomStringUtils.randomNumeric(3);
        return (rString+rNumber);
    }
}
