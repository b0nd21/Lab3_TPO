import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class LoginTest {
    Utils utils;
    WebDriverWait wait;

    private final String gotoAccountBtn = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div";
    private final String accountInitials = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div/div";
    private final String logoutBtn = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[1]/div[2]/button";
    private final String popUpForm = "//*[@id=\"root\"]/div/div/div[1]/div/button";

    private void doCorrectLogin(WebDriver driver){
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        wait.until(ExpectedConditions.textToBe(By.xpath(accountInitials), "МБ")); // проверяем инициалы аккаунта в правом верхнем углу
        driver.findElement(By.xpath(gotoAccountBtn)).click(); // идем на страницу аккаунта
        WebElement exitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(logoutBtn))); // жмем кнопку выйти
        exitBtn.click();
        driver.quit();
    }

    private void doIncorrectLogin(WebDriver driver){
        utils.prepare(driver);
        String window = utils.doLogin(driver, utils.getLogin(), "sfgjyfghjfcghcf");
        driver.switchTo().window(window);
        assertTrue(utils.isElementPresent(driver, By.xpath(popUpForm))); // во всплывающем окне все еще должна быть форма
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void successfulLogin() {
        doCorrectLogin(new FirefoxDriver());
        doCorrectLogin(new ChromeDriver());
    }

    @Test
    public void wrongPassword() {
        doIncorrectLogin(new FirefoxDriver());
        doIncorrectLogin(new ChromeDriver());
    }
}