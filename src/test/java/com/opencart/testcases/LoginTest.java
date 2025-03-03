package com.opencart.testcases;

import com.opencart.pageobjects.HomePage;
import com.opencart.pageobjects.LoginPage;
import com.opencart.pageobjects.MyAccountPage;
import com.opencart.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"Sanity", "Master"})
    public void testLogin() {

        logger.info("Starting test case - testLogin");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked MyAccount link");
            homePage.clickLogin();
            logger.info("Clicked Login link");

            LoginPage loginPage = new LoginPage(driver);
            logger.info("Providing login details");
            loginPage.setTxtEmail(properties.getProperty("email"));
            loginPage.setTxtPassword(properties.getProperty("password"));
            loginPage.clickBtnLogin();

            logger.info("Validating login...");
            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean isMyAccountDisplayed = myAccountPage.isMyAccountDisplayed();
            Assert.assertEquals(isMyAccountDisplayed, true);
            logger.info("Test passed!");
        } catch (Exception e) {
            logger.error("Test failed with exception: " + e.getMessage(), e);
            logger.debug("Test failed due to exception: " + e.getMessage());
            Assert.fail();
        }

        logger.info("Finished executing test case - testLogin");

    }
}
