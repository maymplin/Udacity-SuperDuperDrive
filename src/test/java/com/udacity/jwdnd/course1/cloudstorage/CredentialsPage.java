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

//    public void createNote(String credentialUrl, String credentialUsername, String credentialPassword) {
//        this.credentialsTabButton.click();
//
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
//        driver.findElement(By.id("add-note")).click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("create-note-button")));
//        this.noteTitleField.sendKeys(noteTitle);
//        this.noteDescriptionField.sendKeys(noteDescription);
//        driver.findElement(By.id("create-note-button")).click();
//
//    }
//
//    public String retrieveNoteTitle() {
//        this.notesTabButton.click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
//
//        return this.driver.findElement(By.className("noteTitle")).getText();
//    }
//
//    public void editNoteTitle(String newNoteTitle) {
//        this.notesTabButton.click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
//        this.editNoteButton.click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("create-note-button")));
//        this.noteTitleField.clear();
//        this.noteTitleField.sendKeys(newNoteTitle);
//        this.driver.findElement(By.id("create-note-button")).click();
//    }
//
//    public void deleteNote() {
//        this.notesTabButton.click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
//        this.driver.findElement(By.id("delete-note")).click();
//    }
//
//    public Boolean isNoteDisplayed() {
//        this.notesTabButton.click();
//        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
//
//        return this.driver.findElement(By.id("note-title")).isDisplayed();
//
//    }
}
