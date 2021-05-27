import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class SearchTest {
    Utils utils;
    WebDriverWait wait;

    private final String searchResult = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/div/div[1]/div/div[2]/a/span";
    private final String noResult = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div/div[2]/div[2]/div/div/div/div[1]/div[2]/div/h1";

    private void doNormalSearch(WebDriver driver){
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doSearch(driver, utils.getDefaultQuery());
        WebElement result = driver.findElement(By.xpath(searchResult));
        assertEquals("Галерея", result.getText());
        driver.quit();
    }

    private void doNoResultSearch(WebDriver driver) {
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        utils.doSearch(driver, "zcxczxczxczxczxczxczxczxczxc");
        WebElement result = driver.findElement(By.xpath(noResult));
        assertEquals("Ничего не нашлось, попробуйте уточнить запрос", result.getText());
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void normalSearchTest() {
        doNormalSearch(new FirefoxDriver());
        doNormalSearch(new ChromeDriver());
    }

    @Test
    public void noResultsTest() {
        doNoResultSearch(new FirefoxDriver());
        doNoResultSearch(new ChromeDriver());
    }
}