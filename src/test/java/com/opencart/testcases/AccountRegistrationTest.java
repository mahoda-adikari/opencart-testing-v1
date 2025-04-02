package com.opencart.testcases;

import com.opencart.pageobjects.AccountRegistrationPage;
import com.opencart.pageobjects.HomePage;
import com.opencart.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountRegistrationTest extends BaseTest {

    @Test(groups = {"Regression", "Master"})
    public void testAccountRegistration() {

        getLogger().info("Starting test case - testAccountRegistration");

        try {
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            getLogger().info("Clicked MyAccount link");
            homePage.clickRegister();
            getLogger().info("Clicked Register link");

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());
            getLogger().info("Providing customer details");
            regPage.setTxtFirstName(randomString().toUpperCase());
            regPage.setTxtLastName(randomString().toUpperCase());
            regPage.setTxtEmail(randomString()+"@zmail.com");
            regPage.setTxtTelephone(randomNumber());

            String password = randomAlphaNumeric();
            regPage.setTxtPassword(password);
            regPage.setTxtConfirmPassword(password);

            regPage.setChkdPrivacyPolicy();
            regPage.clickBtnContinue();

            getLogger().info("Validating account registration...");
            String confMsg = regPage.getMsgConfirmation();
            Assert.assertEquals(confMsg, "Your Account Has Been Created!");
            getLogger().info("Test passed!");
        } catch (Throwable e) {
            getLogger().error("Test failed with exception: "+ e.getMessage(), e);
            getLogger().debug("Test failed due to exception: "+ e.getMessage());
            Assert.fail("Test failed with exception: "+ e.getMessage());
        } finally {
            getLogger().info("Finished executing test case - testAccountRegistration");
        }
    }
}
