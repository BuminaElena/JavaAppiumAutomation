import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\user\\Documents\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "couldn't find skipButton",
                5
                );
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
                );
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                "java",
                "couldn't find searchField",
                5
                );
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Couldn't find element by xpath",
                15
        );
        System.out.println("First test run successful");
    }

    @Test
    public void compareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "couldn't find skipButton",
                5
                );
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                "java",
                "couldn't find searchField",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "couldn't find article",
                15
        );
        WebElement titleElement =  waitForElementPresent(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::*"),
                "couldn't find article's title",
                15
        );
        String titleArticle = titleElement.getText();
        Assert.assertEquals(
                "Unexpected title",
                "Java (programming language)",
                titleArticle
        );
    }

    @Test
    public void checkSearchFieldText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "couldn't find skipButton",
                5
                );
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        assertElementHasText(
                By.id("search_src_text"),
                "Search Wikipedia",
                "unexpected text in search field",
                5
        );

    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
         return waitForElementPresent(by, errorMessage,5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private void assertElementHasText(By by, String expectedText, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, "Element was not found" + "\n", timeoutInSeconds);
        Assert.assertEquals(errorMessage, element.getText(), expectedText);
    }

}
