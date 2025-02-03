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
    WebElement chkdPrivacyPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;


    public void setTxtFirstName(String firstName) {
        typeText(this.txtFirstName, firstName);
    }

    public void setTxtLastName(String lastName) {
        typeText(this.txtLastName, lastName);
    }

    public void setTxtEmail(String email) {
        typeText(this.txtEmail, email);
    }

    public void setTxtTelephone(String telephone) {
        typeText(this.txtTelephone, telephone);
    }

    public void setTxtPassword(String password) {
        typeText(this.txtPassword, password);
    }

    public void setTxtConfirmPassword(String confirmPassword) {
        typeText(this.txtConfirmPassword, confirmPassword);
    }

    public void setChkdPrivacyPolicy() {
        clickElement(chkdPrivacyPolicy);
    }

    public void clickBtnContinue() {
        clickElement(btnContinue);
    }

    public String getMsgConfirmation() {
        try {
            return msgConfirmation.getText();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
