package com.opencart.testcases;

import com.opencart.pageobjects.AccountRegistrationPage;
import com.opencart.pageobjects.HomePage;
import com.opencart.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountRegistrationTest extends BaseTest {

    @Test(groups = {"Regression", "Master"})
    public void testAccountRegistration() {

        logger.info("Starting test case - testAccountRegistration");

        try {
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            logger.info("Clicked MyAccount link");
            homePage.clickRegister();
            logger.info("Clicked Register link");

            AccountRegistrationPage regPage = new AccountRegistrationPage(getDriver());
            logger.info("Providing customer details");
            regPage.setTxtFirstName(randomString().toUpperCase());
            regPage.setTxtLastName(randomString().toUpperCase());
            regPage.setTxtEmail(randomString()+"@zmail.com");
            regPage.setTxtTelephone(randomNumber());

            String password = randomAlphaNumeric();
            regPage.setTxtPassword(password);
            regPage.setTxtConfirmPassword(password);

            regPage.setChkdPrivacyPolicy();
            regPage.clickBtnContinue();

            logger.info("Validating account registration...");
            String confMsg = regPage.getMsgConfirmation();
            Assert.assertEquals(confMsg, "Your Account Has Been Created!");
            logger.info("Test passed!");
        } catch (Throwable e) {
            logger.error("Test failed with exception: "+ e.getMessage(), e);
            logger.debug("Test failed due to exception: "+ e.getMessage());
            Assert.fail();
        }

        logger.info("Finished executing test case - testAccountRegistration");
    }
}
