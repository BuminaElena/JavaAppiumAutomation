import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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
                By.id("org.wikipedia:id/view_page_title_text"),
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
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                "Appium",
                "couldn't find searchField",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "couldn't find article",
                15
        );
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can't find footer.",
                20
        );
    }

    @Test
    public void checkSearchFieldText() {
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        assertElementHasText(
                By.className("android.widget.EditText"),
                "Search…",
                "unexpected text in search field",
                5
        );
    }

    @Test
    public void cancelSearch() {
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        // вводим слово в поле поиска
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                "java",
                "couldn't find searchField",
                5
        );
        // ожидаем хотя бы один результат поиска
        waitForElementPresent(
                By.id("page_list_item_title"),
                "couldn't find result of search",
                15
        );
        // нажимаем крестик
        waitForElementAndClick(
                By.id("search_close_btn"),
                "couldn't find closeButton",
                5
        );
        // проверяем, что результат поиска не отображается
        waitForElementNotPresent(
                By.id("page_list_item_title"),
                "result of search is present on page",
                5
        );
    }

    @Test
    public void searchByWord() {
        String search = "appium";
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        // вводим слово в поле поиска
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                search,
                "couldn't find searchField",
                5
        );
        // получаем список результатов поиска
        List<WebElement> result = waitForListOfElementsPresent(
                By.id("page_list_item_title"),
                "couldn't find result of search",
                15
        );
        for (WebElement element : result) {
            String elementText = element.getText().toLowerCase();
            Assert.assertTrue(
                    "Result doesn't contains " + search + ". Result is " + elementText,
                    elementText.contains(search.toLowerCase())
            );
        }
    }

    @Test
    public void saveFirstArticleToMyList() {
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
       waitForElementAndClick(
               By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
               "couldn't find menu button",
               5
       );
       waitForElementAndClick(
               By.xpath("//*[@text='Add to reading list']"),
               "couldn't find option Add to reading list",
               5
       );
       waitForElementAndClick(
               By.id("org.wikipedia:id/onboarding_button"),
               "couldn't find button Got it",
               5
       );
       waitForElementAndClear(
               By.id("org.wikipedia:id/text_input"),
               "couldn't put text in folder input",
               5
       );
       String folderName = "Learning programming";

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
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "couldn't press button X",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView"),
                "couldn't find button My Lists",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "couldn't find created folder",
                15
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "couldn't find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "saved article still present",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search = "Linkin Park Discography";
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
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(searchResultLocator),
                "can't find articles by the request " + search,
                15
        );
        int amount = getAmountOfElements(By.xpath(searchResultLocator));
        Assert.assertTrue("Nothing was found", amount > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String search = "cvghjklkj";
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
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@resource-id='org.wikipedia:id/search_empty_text']";
        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "can't find empty result label by the request " + search,
                15
        );
        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "Was found result by request " + search
                );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String search = "java";
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
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (programming language)']"),
                "couldn't find 'Java (programming language)' by request " + search,
                15
        );
        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can't find title of article",
                15
        );
        Assert.assertEquals("Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground() {
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
                "couldn't find article",
                15
        );
        driver.runAppInBackground(2);
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "couldn't find article after returning from background",
                15
        );
    }

    @Test
    public void saveTwoArticles() {
        String firstSearch = "java",
                secondSearch = "python",
                firstArticleName = "Java (programming language)",
                secondArticleName = "Python (programming language)",
                folderName = "Programming learning";
        findAndSaveArticleToList(
                firstSearch,
                firstArticleName,
                folderName);
        findAndSaveArticleToList(
                secondSearch,
                secondArticleName,
                folderName);
        // переходим в сохраненные списки
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView"),
                "couldn't find button My Lists",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "couldn't find created folder",
                15
        );
        swipeElementToLeft(
                By.xpath("//*[@text='" + firstArticleName + "']"),
                "couldn't find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='" + firstArticleName + "']"),
                "first article still present",
                5
        );
        // кликаем по второй статье
        waitForElementAndClick(
                By.xpath("//*[@text='" + secondArticleName + "']"),
                "second article doesn't present",
                5
        );
        // получаем заголовок статьи
        String title = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Couldn't find attribute",
                5
        );
        // сравниваем заголовок с ожидаемым
        Assert.assertEquals("Title of article doesn't equal expected", title, secondArticleName);

    }

    @Test
    public void checkArticleHasTitle() {
        String search = "java",
                articleName = "Java (programming language)";
        waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Couldn't find searchButton",
                5
        );
        waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                search,
                "Couldn't find searchField",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleName + "']"),
                "Couldn't find article " + articleName,
                15
        );
        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Couldn't find article's title"
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private List<WebElement> waitForListOfElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
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

    private void assertElementHasText(WebElement element, String expectedText) {
        Assert.assertEquals(element.getText(), expectedText);
    }

    protected void swipeUp(int timeOfSwipe) {
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

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
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

    protected void swipeElementToLeft(By by, String errorMessage) {
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

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private void findAndSaveArticleToList(String search, String articleName, String folderName) {
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

    private void assertElementPresent(By by, String errorMessage) {
        int amount = getAmountOfElements(by);
        if (amount == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present. \n";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
