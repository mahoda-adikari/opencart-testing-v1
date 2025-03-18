package com.opencart.testcases;

import com.opencart.pageobjects.HomePage;
import com.opencart.pageobjects.LoginPage;
import com.opencart.pageobjects.MyAccountPage;
import com.opencart.testbase.BaseTest;
import com.opencart.utilities.DataProviderUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenLoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviderUtility.class, groups = "DataDriven")
    public void testLoginDataDriven(String email, String password, String expVal) {

        logger.info("Starting test case - testLoginDataDriven with email: "+email);
        MyAccountPage myAccountPage = null;
        boolean isMyAccountPageDisplayed = false;

        try {
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.setTxtEmail(email);
            loginPage.setTxtPassword(password);
            loginPage.clickBtnLogin();

            myAccountPage = new MyAccountPage(getDriver());
            isMyAccountPageDisplayed = myAccountPage.isMyAccountDisplayed();

            if (expVal.equalsIgnoreCase("Valid")) {
                Assert.assertTrue(isMyAccountPageDisplayed, "Expected valid login to succeed but My Account page was not displayed");
            } else if (expVal.equalsIgnoreCase("Invalid")) {
                Assert.assertFalse(isMyAccountPageDisplayed, "Expected invalid login to fail but My Account page was displayed");
            }

        } catch (Exception e) {
            logger.error("Test failed with exception: "+ e.getMessage(), e);
            logger.debug("Test failed due to exception: "+ e.getMessage());
            Assert.fail("Test failed with exception: "+ e.getMessage());
        } finally {
            try {
                if (isMyAccountPageDisplayed) {
                    logger.info("Performing logout as user was logged in");
                    myAccountPage.clickBtnLogout();
                }
            } catch (Exception e) {
                logger.error("Failed to logout: "+ e.getMessage(), e);
            }
            logger.info("Finished executing test case - testLoginDataDriven with email: "+email);
        }
    }
}
