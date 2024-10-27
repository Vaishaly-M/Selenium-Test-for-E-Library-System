import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IssueBookTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\IdeaProjects\\lms\\src\\test\\java\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("http://localhost/Online-Library-Management-System/library/adminlogin.php");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("Test@123");
        driver.findElement(By.name("login")).click(); // assuming there's a 'submit' button
    }

    @Test
    public void testIssueBook() {
        driver.get("http://localhost/Online-Library-Management-System/library/admin/issue-book.php");

        driver.findElement(By.id("studentid")).sendKeys("SID015");


        driver.findElement(By.id("bookid")).sendKeys("10");


        driver.findElement(By.id("submit")).click();
    }
    @Test
    public void testAlreadyIssuedBook() {
        driver.get("http://localhost/Online-Library-Management-System/library/admin/issue-book.php");

        driver.findElement(By.id("studentid")).sendKeys("SID013");


        driver.findElement(By.id("bookid")).sendKeys("5");


        driver.findElement(By.id("submit")).click();
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
    }
}
