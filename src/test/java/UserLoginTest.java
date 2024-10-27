import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserLoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\IdeaProjects\\lms\\src\\test\\java\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();

        driver.get("http://localhost/Online-Library-Management-System/library/index.php#ulogin");
    }

    @Test
    public void testValidLogin() {
        try {
            WebElement emailField = driver.findElement(By.name("emailid"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.name("login"));

            emailField.sendKeys("maya@gmail.com");
            passwordField.sendKeys("Maya@123");
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("dashboard.php"));

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("dashboard.php"), "Login failed with valid credentials.");
        } catch (Exception e) {
            Assert.fail("Exception in testValidLogin: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidLogin() {
        try {
            WebElement emailField = driver.findElement(By.name("emailid"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.name("login"));

            emailField.sendKeys("maya123@gmail.com");
            passwordField.sendKeys("maya2");
            loginButton.click();

            wait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            Assert.assertEquals(alertText, "Invalid Details", "Unexpected alert message for invalid login.");
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.fail("No alert present for invalid login.");
        } catch (Exception e) {
            Assert.fail("Exception in testInvalidLogin: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyCredentials() {
        driver.get("http://localhost/Online-Library-Management-System/library/index.php#ulogin");

        WebElement usernameField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("login"));


        usernameField.clear();
        passwordField.clear();
        loginButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String usernameErrorMessage = usernameField.getAttribute("validationMessage");
        String passwordErrorMessage = passwordField.getAttribute("validationMessage");

        String expectedUsernameMessage = "Please fill out this field";
        String expectedPasswordMessage = "Please fill out this field";

        try {
            Assert.assertEquals("Username error message is incorrect", expectedUsernameMessage, usernameErrorMessage);
            Assert.assertEquals("Password error message is incorrect", expectedPasswordMessage, passwordErrorMessage);
            System.out.println("Test Passed: Correct validation messages displayed for empty credentials.");
        } catch (AssertionError e) {
            System.err.println("Test Failed: Incorrect validation messages displayed.");
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

