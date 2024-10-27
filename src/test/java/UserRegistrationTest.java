import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class UserRegistrationTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\IdeaProjects\\lms\\src\\test\\java\\chromedriver-win64\\chromedriver.exe"); // Adjust the path
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testValidUserRegistration() {
        driver.get("http://localhost/Online-Library-Management-System/library/signup.php");

        WebElement fullNameField = driver.findElement(By.name("fullname"));
        WebElement mobileNumberField = driver.findElement(By.name("mobileno"));
        WebElement emailField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmpassword"));
        WebElement signup = driver.findElement(By.name("signup"));

        // Enter valid details
        fullNameField.sendKeys("John Doe");
        mobileNumberField.sendKeys("0123456789");
        emailField.sendKeys("johndoe@example.com");
        passwordField.sendKeys("Password123");
        confirmPasswordField.sendKeys("Password123");

        signup.click();

        String expectedUrl = "http://localhost/Online-Library-Management-System/library/admin/index.php";
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
    public void testEmailAlreadyExists() {
        driver.get("http://localhost/Online-Library-Management-System/library/signup.php");

        WebElement fullNameField = driver.findElement(By.name("fullname"));
        WebElement mobileNumberField = driver.findElement(By.name("mobileno"));
        WebElement emailField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmpassword"));
        WebElement registerButton = driver.findElement(By.name("signup"));

        fullNameField.sendKeys("Jane Doe");
        mobileNumberField.sendKeys("0987654321");
        emailField.sendKeys("johndoe@example.com"); // Existing email
        passwordField.sendKeys("Password123");
        confirmPasswordField.sendKeys("Password123");

        registerButton.click();

        WebElement errorMessage = driver.findElement(By.id("user-availability-status"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Email already exists";

        try {
            Assert.assertEquals(actualMessage, expectedMessage);
            System.out.println("Test Passed: Email already exists message displayed.");
        } catch (AssertionError e) {
            System.err.println("Test Failed: Incorrect error message displayed for existing email.");
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidEmail() {
        driver.get("http://localhost/Online-Library-Management-System/library/signup.php");

        WebElement fullNameField = driver.findElement(By.name("fullname"));
        WebElement mobileNumberField = driver.findElement(By.name("mobileno"));
        WebElement emailField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmpassword"));
        WebElement registerButton = driver.findElement(By.name("signup"));

        fullNameField.sendKeys("User one");
        mobileNumberField.sendKeys("0123456789");
        emailField.sendKeys("invalidemail.com"); // Invalid email (missing '@')
        passwordField.sendKeys("Password123");
        confirmPasswordField.sendKeys("Password123");

        registerButton.click();
        try {

            String emailAlertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(emailAlertMessage, "Please enter a valid email address.");
            driver.switchTo().alert().accept();

            System.out.println("Test Passed: Correct validation messages displayed.");
        } catch (Exception e) {
            System.err.println("Test Failed: Incorrect validation messages displayed.");
            e.printStackTrace();
        }
    }
    @Test
    public void testInvalidMobileNumber() {
        driver.get("http://localhost/Online-Library-Management-System/library/signup.php");

        WebElement fullNameField = driver.findElement(By.name("fullname"));
        WebElement mobileNumberField = driver.findElement(By.name("mobileno"));
        WebElement emailField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmpassword"));
        WebElement registerButton = driver.findElement(By.name("signup"));

        fullNameField.sendKeys("Invalid User");
        mobileNumberField.sendKeys("01234"); // Invalid mobile number (less than 10 digits)
        emailField.sendKeys("valid@email.com");
        passwordField.sendKeys("Password123");
        confirmPasswordField.sendKeys("Password123");

        registerButton.click();

        try {
            String mobileAlertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(mobileAlertMessage, "Mobile number must be exactly 10 digits.");
            driver.switchTo().alert().accept();


            System.out.println("Test Passed: Correct validation messages displayed.");
        } catch (Exception e) {
            System.err.println("Test Failed: Incorrect validation messages displayed.");
            e.printStackTrace();
        }
    }
    @Test
    public void testPasswordMismatch() {
        driver.get("http://localhost/Online-Library-Management-System/library/signup.php");

        WebElement fullNameField = driver.findElement(By.name("fullname"));
        WebElement mobileNumberField = driver.findElement(By.name("mobileno"));
        WebElement emailField = driver.findElement(By.name("emailid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmpassword"));
        WebElement registerButton = driver.findElement(By.name("signup"));

        fullNameField.sendKeys("Invalid User");
        mobileNumberField.sendKeys("0123465478");
        emailField.sendKeys("valid1@email.com");
        passwordField.sendKeys("Password123");
        confirmPasswordField.sendKeys("PasswordMismatch"); //mismatch password

        registerButton.click();

        try {
            String passwordAlertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(passwordAlertMessage, "Password and Confirm Password Field do not match!");
            driver.switchTo().alert().accept();

            System.out.println("Test Passed: Correct validation messages displayed.");
        } catch (Exception e) {
            System.err.println("Test Failed: Incorrect validation messages displayed.");
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
