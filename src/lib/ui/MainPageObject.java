package lib.ui;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class MainPageObject {
    
    protected AppiumDriver driver;
    
    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }
    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public List<WebElement> waitForListOfElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage,5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void assertElementHasText(By by, String expectedText, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, "Element was not found" + "\n", timeoutInSeconds);
        Assert.assertEquals(errorMessage, element.getText(), expectedText);
    }

    public void assertElementHasText(WebElement element, String expectedText) {
        Assert.assertEquals(element.getText(), expectedText);
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size()==0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Can't find element by swiping. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().width;
        int upperY = element.getLocation().getY();
        int middleY = upperY + element.getSize().height / 2;
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        action
                .press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void findAndSaveArticleToList(String search, String articleName, String folderName) {
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                search,
                "couldn't find searchField",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + articleName + "']"),
                "couldn't find article " + articleName,
                15
        );
        // дожидаемся появления заголовка статьи (иначе некорректно срабатывает нажатие на меню)
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "couldn't find article's title" + articleName,
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "couldn't find menu button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "couldn't find option Add to reading list",
                10
        );
        // если списка еще не существует, создаем его
        if (getAmountOfElements(By.id("org.wikipedia:id/onboarding_button")) == 1) {
            waitForElementAndClick(
                    By.id("org.wikipedia:id/onboarding_button"),
                    "couldn't find button Got it",
                    5
            );
            waitForElementAndClear(
                    By.id("org.wikipedia:id/text_input"),
                    "couldn't clear text in folder input",
                    5
            );
            waitForElementAndSendKeys(
                    By.id("org.wikipedia:id/text_input"),
                    folderName,
                    "couldn't put text in folder input",
                    5
            );
            waitForElementAndClick(
                    By.xpath("//*[@text='OK']"),
                    "couldn't press button OK",
                    5
            );
        } else {  // если список уже существует, выбираем его
            waitForElementAndClick(
                    By.xpath("//*[@text='" + folderName + "']"),
                    "couldn't click on folder " + folderName,
                    5
            );
        }
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "couldn't press button X",
                5
        );
    }

    public void assertElementPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present. \n";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
