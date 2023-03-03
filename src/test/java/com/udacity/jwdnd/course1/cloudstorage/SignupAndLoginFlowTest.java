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
public class SignupAndLoginFlowTest {

    @LocalServerPort
    private int port;
    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static final String baseUrl = "http://localhost:";
    private static final String firstName = "Bertrand";
    private static final String lastName = "Russell";
    private static final String username = "brussell";
    private static final String password = "password";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
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
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @Test
    public void signupLoginHomeAccessTest() {

        webDriverWait = new WebDriverWait(driver, 3);

        // Sign-up new user
        driver.get(baseUrl + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        SignupPage signupPage = new SignupPage(driver);
        doMockSignup(firstName, lastName, username, password);

        Assertions.assertTrue(signupPage.isSignupSuccessful());

        // Log in
        driver.get(baseUrl + this.port + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        doMockLogin(username, password);

        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    public void unauthorizedAccessTest() {
        driver.get(baseUrl + this.port + "/signup");
        doMockSignup(firstName, lastName, username, password);
        webDriverWait = new WebDriverWait(driver, 3);
        driver.get(baseUrl + this.port + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        doMockLogin(username, password);

        // Log out
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logout();

        // Verify user is logged out
        Assertions.assertEquals(baseUrl + this.port + "/login", driver.getCurrentUrl());

        // Try to access home page
        driver.get(baseUrl + this.port + "/home");

        // Should be redirected to login page
        Assertions.assertEquals(baseUrl + this.port + "/login", driver.getCurrentUrl());
    }
}
