package com.opencart.testcases;

import com.opencart.pageobjects.AccountRegistrationPage;
import com.opencart.pageobjects.HomePage;
import com.opencart.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountRegistrationTest extends BaseTest {

    @Test
    public void testAccountRegistration() {
        HomePage homePage = new HomePage(driver);
        homePage.clickMyAccount();
        homePage.clickRegister();

        AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
        regPage.setTxtFirstName(randomString().toUpperCase());
        regPage.setTxtLastName(randomString().toUpperCase());
        regPage.setTxtEmail(randomString()+"@zmail.com");
        regPage.setTxtTelephone(randomNumber());

        String password = randomAlphaNumeric();
        regPage.setTxtPassword(password);
        regPage.setTxtConfirmPassword(password);

        regPage.setChkdPrivacyPolicy();
        regPage.setBtnContinue();

        String confMsg = regPage.getMsgConfirmation();
        Assert.assertEquals(confMsg, "Your Account Has Been Created!");
    }
}
