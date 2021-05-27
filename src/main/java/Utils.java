
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Utils {
    private final String url = "https://2gis.ru/spb";
    private final String login = "bondMB@yandex.ru";
    private final String password = "lab3tpo";
    private final String defaultQuery = "Галерея";

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDefaultQuery() {
        return defaultQuery;
    }

    public Utils() {
        System.setProperty("webdriver.gecko.driver","src\\main\\resources\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
    }

    public void prepare(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(getUrl());
    }

    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String  doLogin(WebDriver driver, String login, String password) {
        String originalWindow = driver.getWindowHandle();
        Set<String> oldWindowsSet = driver.getWindowHandles();

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[3]/div[1]/div/div[2]/div[4]/div/div/div/div")).click();

        String newWindow = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        driver.switchTo().window(newWindow);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div/div/div/div[2]/div[8]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div/div/form/div[1]/input")).sendKeys(login);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div/div/form/div[2]/div/input")).sendKeys(password);;
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div/div/form/div[4]/button")).click();
        driver.switchTo().window(originalWindow);
        return  newWindow;
    }

    public void doSearch(WebDriver driver, String query) {
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div/div[1]/div/div/div/div/div[2]/form/div/input"));
        searchBar.sendKeys(query);
        searchBar.sendKeys(Keys.ENTER);
    }

    public void acceptAlert(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.alertIsPresent()); // подтверждаем удаление во всплывающем алерте
        Alert alert = driver.switchTo().alert();
        alert.accept();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearField(WebDriver driver, WebElement textField) {
        if(driver instanceof FirefoxDriver)
            textField.clear();
        else
            textField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }
}
