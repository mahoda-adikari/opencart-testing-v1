package com.opencart.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkBoxPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;


}
