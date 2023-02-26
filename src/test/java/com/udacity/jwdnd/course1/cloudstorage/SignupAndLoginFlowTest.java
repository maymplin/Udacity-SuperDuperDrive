package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
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

    @Test
    public void unauthorizedAccessTest() {

        driver.get(baseUrl + port + "/home");

    }

    @Test
    public void signupLoginHomeAccessTest() {

        String firstName = "Bertrand";
        String lastName = "Russell";
        String username = "brussell";
        String password = "newPassword";

        webDriverWait = new WebDriverWait(driver, 3);

        // Sign-up new user
        driver.get(baseUrl + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password);

        Assertions.assertTrue(signupPage.isSignupSuccessful());

        // Log in user
//        driver.get(baseUrl + this.port + "/login");
//        webDriverWait.until(ExpectedConditions.titleContains("Login"));


    }



}
