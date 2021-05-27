import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangeNameTest {
    Utils utils;
    WebDriverWait wait;

    private final String gotoAccountBtn = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div";
    private final String accountInitials = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div/div";
    private final String editAccount = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[1]/div[2]/div/button";
    private final String nameField = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div[2]/div/div[1]/div[2]/div/div/input";
    private final String saveButton =  "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div[3]/div/div/button";
    private final String userName = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[1]/div[1]/div[1]/h1";

    private void changeName(WebDriver driver, String name, String initials) {
        wait = new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        wait.until(ExpectedConditions.textToBe(By.xpath(accountInitials), initials));
        driver.findElement(By.xpath(gotoAccountBtn)).click();
        driver.findElement(By.xpath(editAccount)).click();
        WebElement input = driver.findElement(By.xpath(nameField));
        utils.clearField(driver, input);
        input.sendKeys(name);
        driver.findElement(By.xpath(saveButton)).click();
        wait.until(ExpectedConditions.textToBe(By.xpath(userName), name + " Бондаренко"));
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void changeNameTest() {
        changeName(new FirefoxDriver(), "Иван", "МБ");
        changeName(new FirefoxDriver(), "Михаил", "ИБ");
        changeName(new ChromeDriver(), "Иван", "МБ");
        changeName(new ChromeDriver(), "Михаил", "ИБ");
    }
}