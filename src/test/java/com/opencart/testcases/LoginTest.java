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

        getLogger().info("Starting test case - testLogin");

        try {
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            getLogger().info("Clicked MyAccount link");
            homePage.clickLogin();
            getLogger().info("Clicked Login link");

            LoginPage loginPage = new LoginPage(getDriver());
            getLogger().info("Providing login details");
            loginPage.setTxtEmail(getProperty("email"));
            loginPage.setTxtPassword(getProperty("password"));
            loginPage.clickBtnLogin();

            getLogger().info("Validating login...");
            MyAccountPage myAccountPage = new MyAccountPage(getDriver());
            boolean isMyAccountDisplayed = myAccountPage.isMyAccountDisplayed();
            Assert.assertTrue(isMyAccountDisplayed);
            getLogger().info("Test passed!");
        } catch (Exception e) {
            getLogger().error("Test failed with exception: " + e.getMessage(), e);
            getLogger().debug("Test failed due to exception: " + e.getMessage());
            Assert.fail("Test failed with exception: "+ e.getMessage());
        } finally {
            getLogger().info("Finished executing test case - testLogin");
        }
    }
}
