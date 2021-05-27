import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.junit.Assert.*;

public class CommentTest {
    Utils utils;
    WebDriverWait wait;

    private final String searchResult = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/div/div[1]/div/div[2]/a/span";
    private final String arrow = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div[1]/div[2]/div";
    private final String commentsSection = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div[1]/div[3]/div/div[1]/div[4]";
    private final String addComment = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div/div[2]/div/button";
    private final String fiveStar = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div/div[2]/div[1]/div[2]/div[5]";
    private final String commentTextField = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div/div[2]/div[2]/div[2]/div/div/div/textarea";
    private final String commentSubmitBtn = "//*[@id=\"root\"]/div/div/div[3]/div[1]/div/div/div/div/div[3]/div/div[2]/button";
    private final String gotoAccountBtn = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div";
    private final String accountCommentsSection = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[2]/div/div[3]/div/div[1]/div[3]/div";
    private final String commentText = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div/div[4]/div[1]/a";
    private final String editButton = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div/div[4]/div[2]/div/div[3]";
    private final String deleteButton = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div/div[4]/div[2]/div/div[4]";
    private final String accountInitials = "//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div/div";
    private final String noComments = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div/div/div/div[1]/div[3]/div/div[2]";

    private void addComment(WebDriver driver){
        wait =  new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        utils.doSearch(driver, utils.getDefaultQuery());
        driver.findElement(By.xpath(searchResult)).click(); // перешли на страницу места
        driver.findElement(By.xpath(arrow)).click(); // нажали на стрелочку в меню чтобы открыть отзывы
        driver.findElement(By.xpath(commentsSection)).click(); // зашли во вкладку отзывов
        driver.findElement(By.xpath(addComment)).click(); // добавить отзыв
        driver.findElement(By.xpath(fiveStar)).click();
        driver.findElement(By.xpath(fiveStar)).click(); // даблклик на 5 звездочек

        String comment = "Тестовый комментарий на 20+ символов";
        driver.findElement(By.xpath(commentTextField)).sendKeys(comment); // добавление коммента
        driver.findElement(By.xpath(commentSubmitBtn)).click(); // отправляем

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(commentTextField))); // ждем пока пропадет окно добавления отзыва

        driver.findElement(By.xpath(gotoAccountBtn)).click(); // идем в наш аккаунт и заходим в секцию отзывов
        driver.findElement(By.xpath(accountCommentsSection)).click();

        String msg = driver.findElement(By.xpath(commentText)).getText(); // проверяем что там правильный текст
        assertEquals(comment, msg);
        driver.quit();
    }

    private void editComment(WebDriver driver){
        wait =  new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        wait.until(ExpectedConditions.textToBe(By.xpath(accountInitials), "МБ"));
        driver.findElement(By.xpath(gotoAccountBtn)).click(); // идем в наш аккаунт и заходим в секцию отзывов
        driver.findElement(By.xpath(accountCommentsSection)).click();
        driver.findElement(By.xpath(editButton)).click(); // жмем на кнопку редактировать отзыв
        WebElement textField = driver.findElement(By.xpath(commentTextField));
        String comment = "Новый комментарий на 20+ символов";
        utils.clearField(driver, textField);
        textField.sendKeys(comment); // записываем новый
        driver.findElement(By.xpath(commentSubmitBtn)).click(); // подтверждаем

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(commentTextField)));
        String msg = driver.findElement(By.xpath(commentText)).getText(); // проверяем что отзыв изменился
        assertEquals(comment, msg);
        driver.quit();
    }

    private void deleteComment(WebDriver driver){
        wait =  new WebDriverWait(driver, 20);
        utils.prepare(driver);
        utils.doLogin(driver, utils.getLogin(), utils.getPassword());
        wait.until(ExpectedConditions.textToBe(By.xpath(accountInitials), "МБ"));
        driver.findElement(By.xpath(gotoAccountBtn)).click(); // идем в наш аккаунт и заходим в секцию отзывов
        driver.findElement(By.xpath(accountCommentsSection)).click();
        driver.findElement(By.xpath(deleteButton)).click(); // жмем на кнопку удаления

        utils.acceptAlert(driver, wait); // подтверждаем на алерте
        assertEquals("Отзывов пока нет", driver.findElement(By.xpath(noComments)).getText());
        driver.quit();
    }

    @Before
    public void setUp() {
        utils = new Utils();
    }


    @Test
    public void commentTest() {
        addComment(new FirefoxDriver());
        editComment(new FirefoxDriver());
        deleteComment(new FirefoxDriver());
        addComment(new ChromeDriver());
        editComment(new ChromeDriver());
        deleteComment(new ChromeDriver());
    }


}