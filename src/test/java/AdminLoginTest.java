import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AdminLoginTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\ADMIN\\IdeaProjects\\lms\\src\\test\\java\\chromedriver-win64\\chromedriver.exe"); // Adjust the path
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testAdminLogin() {
        driver.get("http://localhost/Online-Library-Management-System/library/adminlogin.php");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("login"));

        // Enter credentials
        usernameField.sendKeys("admin"); // Replace with your username
        passwordField.sendKeys("Test@123"); // Replace with your password

        loginButton.click();

        String expectedUrl = "http://localhost/Online-Library-Management-System/library/admin/dashboard.php";
        String actualUrl = driver.getCurrentUrl();

        try {
            Assert.assertEquals(actualUrl, expectedUrl);
            System.out.println("Test Passed: Successfully navigated to the dashboard.");
        } catch (AssertionError e) {
            System.err.println("Test Failed: Did not navigate to the dashboard.");
            e.printStackTrace();
        }
    }
    @Test
    public void testAdminLoginInvalidCredentials() {
        driver.get("http://localhost/Online-Library-Management-System/library/adminlogin.php");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("login"));

        usernameField.sendKeys("invalidUser");
        passwordField.sendKeys("wrongPassword");

        loginButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String actualMessage = driver.switchTo().alert().getText();

        String expectedMessage = "Invalid Details";
        try {
            Assert.assertEquals(actualMessage, expectedMessage);
            System.out.println("Test Passed: Correct alert message displayed for invalid credentials.");
        } catch (AssertionError e) {
            System.err.println("Test Failed: Incorrect alert message displayed.");
            e.printStackTrace();
        }

        driver.switchTo().alert().accept();
    }

    @Test
    public void testAdminLoginEmptyCredentials() {
        driver.get("http://localhost/Online-Library-Management-System/library/adminlogin.php");

        WebElement usernameField = driver.findElement(By.name("username"));
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
            Assert.assertEquals(usernameErrorMessage, expectedUsernameMessage);
            Assert.assertEquals(passwordErrorMessage, expectedPasswordMessage);
            System.out.println("Test Passed: Correct validation messages displayed for empty credentials.");
        } catch (AssertionError e) {
            System.err.println("Test Failed: Incorrect validation messages displayed.");
            e.printStackTrace();
        }
    }
    @AfterClass
    public void tearDown() {
        //driver.quit();
    }
}
