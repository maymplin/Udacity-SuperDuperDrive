package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesAndCredentialsTest {

    @LocalServerPort
    private int port;
    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static NotesPage notesPage;
    private static LoginPage loginPage;
    private static String baseUrl = "http://localhost:";
    private static final String firstName = "Godfrey";
    private static final String lastName = "Hardy";
    private static final String username = "ghhardy";
    private static final String password = "test123";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 3);
        baseUrl += port;
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    private void doMockSignup(String firstName, String lastName, String username, String password) {
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password);
    }

    private void doMockLogin(String username, String password) {
//        loginPage = new LoginPage(driver);
        driver.get(baseUrl + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        loginPage.login(username, password);
    }

    private void doMockLogout() {
        loginPage.logout();
    }

    @Test
    public void noteCreationEditDeleteTest() {
        notesPage = new NotesPage(driver, webDriverWait);

        // Sign-up and log-in
        driver.get(baseUrl + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        SignupPage signupPage = new SignupPage(driver);
        doMockSignup(firstName, lastName, username, password);
        doMockLogin(username, password);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        // Create note
        notesPage.createNote("Test", "Hello world!");

        // Logout and re-login
        doMockLogout();
        doMockLogin(username, password);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        // Verify note is visible
        Assertions.assertEquals("Test", notesPage.retrieveNoteTitle());
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        // Edit note by changing its title
        notesPage.editNoteTitle("Edited Test");

        // Verify note is edited
        Assertions.assertEquals("Edited Test", notesPage.retrieveNoteTitle());

        // Logout and re-login
        doMockLogout();
        doMockLogin(username, password);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        // Delete note
        notesPage.deleteNote();

        // Verify note is deleted by checking that it is no longer displayed
        Assertions.assertFalse(notesPage.isNoteDisplayed());
    }
}
