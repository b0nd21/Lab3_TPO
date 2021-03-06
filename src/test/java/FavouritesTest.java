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

public class FavouritesTest {
    Utils utils;
    WebDriverWait wait;

    private final String searchResult = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/div/div[1]/div/div[2]/a/span";
    private final String bookmarkBtn = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div/div/div/div/div[1]/div/div[1]/div[2]/div[1]/ul/li[1]/button";
    private final String gotoAccountBtn = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div";
    private final String accountInitials = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div/div";
    private final String favourites = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div[4]/div/div";
    private final String favouritesFirstElement = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div/div/div/div/div[1]/div/div[2]/div/div/div/div[1]/div[1]/button";
    private final String bookmarksMenuLower = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div[2]/div/div[2]";
    private final String bookmarksMenuHigher = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div[2]/div/div[3]";
    private final String bookmarksAcceptBtn = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div[3]/div/div[2]/button";
    private final String bookmarksChangeBtn = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/button";
    private final String addCollection = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div[3]/button";
    private final String collectionInput = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/form/div[1]/div/div[1]/div[2]/div/div/div/input";
    private final String acceptAddCollection = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/form/div[2]/div/button";
    private final String customCollection = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div[4]/div[1]";
    private final String deleteButton = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div[4]/div[1]/div[2]/div/button[2]";

    private void addToFav(WebDriver driver){
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        utils.doSearch(driver, utils.getDefaultQuery()); // ???????????????????? ?? ??????????????????
        driver.findElement(By.xpath(searchResult)).click();
        driver.findElement(By.xpath(bookmarkBtn)).click();
        driver.findElement(By.xpath(gotoAccountBtn)).click();
        driver.findElement(By.xpath(favourites)).click();
        WebElement result = driver.findElement(By.xpath(favouritesFirstElement));
        assertEquals("??????????????", result.getText());

        result.click(); // ????????????????
        driver.findElement(By.xpath(bookmarkBtn)).click();
        driver.findElement(By.xpath(bookmarksMenuLower)).click();
        driver.findElement(By.xpath(bookmarksAcceptBtn)).click();

        driver.quit();
    }


    //!!!!!!! ?????????????????????? ?? ??????????????????
    private void addToCustomCollection(WebDriver driver) {
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());

        wait.until(ExpectedConditions.textToBe(By.xpath(accountInitials), "????"));

        driver.findElement(By.xpath(gotoAccountBtn)).click(); // ?????????????? ??????????????????
        driver.findElement(By.xpath(addCollection)).click();
        driver.findElement(By.xpath(collectionInput)).sendKeys("test");
        driver.findElement(By.xpath(acceptAddCollection)).click();

        utils.doSearch(driver, utils.getDefaultQuery()); // ??????????

        driver.findElement(By.xpath(searchResult)).click(); // ?????????????????? ?? ????????????????, ???? ???????? "????????????????" ?? ???????????? ???????????????????? ?????????????????? ?? ?????????????????? ??????????????????
        driver.findElement(By.xpath(bookmarkBtn)).click();
        driver.findElement(By.xpath(bookmarksChangeBtn)).click();
        driver.findElement(By.xpath(bookmarksMenuHigher)).click();
        driver.findElement(By.xpath(bookmarksMenuLower)).click();
        driver.findElement(By.xpath(bookmarksAcceptBtn)).click();

        driver.findElement(By.xpath(gotoAccountBtn)).click(); // ??????????????????, ?????? ????????????????????
        driver.findElement(By.xpath(customCollection)).click();
        WebElement result = driver.findElement(By.xpath(favouritesFirstElement));
        assertEquals("??????????????", result.getText());

        driver.findElement(By.xpath(customCollection)).click(); // ?????????????? ?????????????????? ??????????????????
        driver.findElement(By.xpath(deleteButton)).click();
        utils.acceptAlert(driver, wait); // ???????????????????????? ???? ????????????
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void addToFavTest() {
        addToFav(new FirefoxDriver());
        addToFav(new ChromeDriver());
    }

    @Test
    public void customCollectionsTest() {
        addToCustomCollection(new FirefoxDriver());
        addToCustomCollection(new ChromeDriver());
    }
}