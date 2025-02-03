package com.opencart.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//span[normalize-space()='My Account']")
    WebElement lnkMyAccount;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement lnkRegister;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
    WebElement lnkLogin;


    public void clickMyAccount(){
        clickElement(lnkMyAccount);
    }

    public void clickRegister(){
        clickElement(lnkRegister);
    }

    public void clickLogin(){
        clickElement(lnkLogin);
    }
}
