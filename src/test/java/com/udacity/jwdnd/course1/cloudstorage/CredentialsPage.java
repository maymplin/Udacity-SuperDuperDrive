package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialsPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabButton;
    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;
    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;
    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;
    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public CredentialsPage(WebDriver driver, WebDriverWait webDriverWait) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.webDriverWait = webDriverWait;
    }

    public void createCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        this.credentialsTabButton.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));
        driver.findElement(By.id("add-credential")).click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("create-cred-button")));
        this.credentialUrlField.sendKeys(credentialUrl);
        this.credentialUsernameField.sendKeys(credentialUsername);
        this.credentialPasswordField.sendKeys(credentialPassword);
        driver.findElement(By.id("create-cred-button")).click();

    }

    public String retrieveCredentialUsername() {
        this.credentialsTabButton.click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));

        return this.driver.findElement(By.className("credential-username")).getText();
    }

    public void editCredentialUsername(String newUsername) {
        this.credentialsTabButton.click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));
        this.editCredentialButton.click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("create-cred-button")));
        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(newUsername);
        this.driver.findElement(By.id("create-cred-button")).click();
    }

    public void deleteCredential() {
        this.credentialsTabButton.click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));
        this.driver.findElement(By.className("delete-credential")).click();
    }

    public Boolean isCredentialDisplayed() {
        this.credentialsTabButton.click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));

        return this.driver.findElement(By.id("credential-url")).isDisplayed();

    }
}
