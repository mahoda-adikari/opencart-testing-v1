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


    public String getMyAccConfirmation() {
        try {
            return myAccConfirmation.getText();
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
