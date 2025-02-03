package com.opencart.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;


    public void setTxtEmail(String email){
        typeText(this.txtEmail, email);
    }

    public void setTxtPassword(String password){
        typeText(txtPassword, password);
    }

    public void clickBtnLogin(){
        clickElement(btnLogin);
    }
}
