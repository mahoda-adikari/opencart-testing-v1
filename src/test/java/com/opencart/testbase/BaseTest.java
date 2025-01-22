package com.opencart.testbase;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;

    @BeforeClass
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
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
