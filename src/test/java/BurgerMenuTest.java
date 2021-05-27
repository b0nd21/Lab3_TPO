import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class BurgerMenuTest {
    Utils utils;
    WebDriverWait wait;

    private final String burger = "//*[@id=\"root\"]/div/div/div[2]/button";
    private final String cityGuideBtn = "//*[@id=\"root\"]/div/div/div[2]/div[5]/div/div[2]/div/div[1]/ul[2]/li[1]/div[2]/button";
    private final String interestingPlacesBtn = "//*[@id=\"root\"]/div/div/div[2]/div[5]/div/div[2]/div/div[1]/ul[2]/li[2]/div[2]/button";
    private final String head = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div";

    private void doCityGuide(WebDriver driver) {
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        driver.findElement(By.xpath(burger)).click();
        driver.findElement(By.xpath(cityGuideBtn)).click();
        assertEquals("Путеводитель по Санкт-Петербургу", driver.findElement(By.xpath(head)).getText());
        driver.quit();
    }

    private void doInterestingPlaces(WebDriver driver){
        wait =  new WebDriverWait(driver, 10);
        utils.prepare(driver);
        driver.findElement(By.xpath(burger)).click();
        driver.findElement(By.xpath(interestingPlacesBtn)).click();
        assertEquals("Интересное в городе", driver.findElement(By.xpath(head)).getText());
        driver.quit();
    }



    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void cityGuideTest() {
        doCityGuide(new FirefoxDriver());
        doCityGuide(new ChromeDriver());
    }

    @Test
    public void interestingPlacesTest() {
        doInterestingPlaces(new FirefoxDriver());
        doInterestingPlaces(new ChromeDriver());
    }
}