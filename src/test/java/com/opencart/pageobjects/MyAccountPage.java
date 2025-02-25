package com.opencart.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

    public MyAccountPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement myAccConfirmation;

    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
    WebElement btnLogout;


    public Boolean isMyAccountDisplayed() {
        try {
            return myAccConfirmation.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    public void clickBtnLogout () {
        clickElement(btnLogout);
    }
}
