import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AddBookTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\IdeaProjects\\lms\\src\\test\\java\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        // Log in as admin
        driver.get("http://localhost/Online-Library-Management-System/library/adminlogin.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("Test@123");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void testAddBookPositive() {
        driver.get("http://localhost/Online-Library-Management-System/library/admin/add-book.php");

        WebElement bookNameField = driver.findElement(By.name("bookname"));
        WebElement categoryField = driver.findElement(By.name("category"));
        WebElement authorField = driver.findElement(By.name("author"));
        WebElement isbnField = driver.findElement(By.name("isbn"));
        WebElement priceField = driver.findElement(By.name("price"));
        WebElement uploadElement = driver.findElement(By.name("bookpic"));
        WebElement addButton = driver.findElement(By.id("add"));

        // Enter valid details
        bookNameField.sendKeys("Test Automation Book");
        categoryField.sendKeys("Programming");
        authorField.sendKeys("John Doe");
        isbnField.sendKeys("1234567890123");
        priceField.sendKeys("19.99");
        uploadElement.sendKeys("C:\\xampp\\htdocs\\Online-Library-Management-System\\Images\\Silent_Spring.jpeg");

        addButton.click();

        try {
            String alertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(alertMessage, "Book Listed successfully");
            driver.switchTo().alert().accept();
            System.out.println("Test Passed: Book added successfully.");
        } catch (Exception e) {
            System.err.println("Test Failed: Book was not added successfully.");
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBookInvalidPrice() {
        driver.get("http://localhost/Online-Library-Management-System/library/admin/add-book.php");

        WebElement bookNameField = driver.findElement(By.name("bookname"));
        WebElement categoryField = driver.findElement(By.name("category"));
        WebElement authorField = driver.findElement(By.name("author"));
        WebElement isbnField = driver.findElement(By.name("isbn"));
        WebElement priceField = driver.findElement(By.name("price"));
        WebElement uploadElement = driver.findElement(By.name("bookpic"));
        WebElement addButton = driver.findElement(By.id("add"));

        // Enter invalid price
        bookNameField.sendKeys("Test Automation Book");
        categoryField.sendKeys("Programming");
        authorField.sendKeys("John Doe");
        isbnField.sendKeys("1234567890123");
        priceField.sendKeys("invalid_price"); // Invalid price format
        uploadElement.sendKeys("C:\\xampp\\htdocs\\Online-Library-Management-System\\Images\\Silent_Spring.jpeg");

        addButton.click();

        try {
            String alertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(alertMessage, "Price must be a valid float number with up to 2 decimal points.");
            driver.switchTo().alert().accept();
            System.out.println("Test Passed: Correct alert displayed for invalid price.");
        } catch (Exception e) {
            System.err.println("Test Failed: Incorrect alert or no alert displayed for invalid price.");
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBookEmptyFields() {
        driver.get("http://localhost/Online-Library-Management-System/library/admin/add-book.php");

        WebElement addButton = driver.findElement(By.id("add"));
        addButton.click();

        try {
            WebElement invalidField = driver.findElement(By.cssSelector(".form-control:invalid"));
            String validationMessage = invalidField.getAttribute("validationMessage");
            Assert.assertEquals(validationMessage, "Please fill out this field.");
            System.out.println("Test Passed: Correct validation message displayed for empty fields.");
        } catch (Exception e) {
            System.err.println("Test Failed: Incorrect or no validation message displayed for empty fields.");
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
