import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import lib.CoreTestCase;
import lib.ui.MainPageObject;

public class FirstTest extends CoreTestCase {
    
    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }


    @Test
    public void testSearchFieldText() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        mainPageObject.assertElementHasText(
                By.className("android.widget.EditText"),
                "Search…",
                "unexpected text in search field",
                5
        );
    }


    @Test
    public void testSearchByWord() {
        String search = "appium";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "couldn't find searchButton",
                5
        );
        // вводим слово в поле поиска
        mainPageObject.waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                search,
                "couldn't find searchField",
                5
        );
        // получаем список результатов поиска
        List<WebElement> result = mainPageObject.waitForListOfElementsPresent(
                By.id("page_list_item_title"),
                "couldn't find result of search",
                15
        );
        for (WebElement element : result) {
            String elementText = element.getText().toLowerCase();
            assertTrue(
                    "Result doesn't contains " + search + ". Result is " + elementText,
                    elementText.contains(search.toLowerCase())
            );
        }
    }

    @Test
    public void testSaveTwoArticles() {
        String firstSearch = "java",
                secondSearch = "python",
                firstArticleName = "Java (programming language)",
                secondArticleName = "Python (programming language)",
                folderName = "Programming learning";
        mainPageObject.findAndSaveArticleToList(
                firstSearch,
                firstArticleName,
                folderName);
        mainPageObject.findAndSaveArticleToList(
                secondSearch,
                secondArticleName,
                folderName);
        // переходим в сохраненные списки
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView"),
                "couldn't find button My Lists",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "couldn't find created folder",
                15
        );
        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + firstArticleName + "']"),
                "couldn't find saved article"
        );
        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + firstArticleName + "']"),
                "first article still present",
                5
        );
        // кликаем по второй статье
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + secondArticleName + "']"),
                "second article doesn't present",
                5
        );
        // получаем заголовок статьи
        String title = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Couldn't find attribute",
                5
        );
        // сравниваем заголовок с ожидаемым
        assertEquals("Title of article doesn't equal expected", title, secondArticleName);

    }

    @Test
    public void testCheckArticleHasTitle() {
        String search = "java",
                articleName = "Java (programming language)";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Couldn't find searchButton",
                5
        );
        mainPageObject.waitForElementAndSendKeys(
                By.className("android.widget.EditText"),
                search,
                "Couldn't find searchField",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleName + "']"),
                "Couldn't find article " + articleName,
                15
        );
        mainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Couldn't find article's title"
        );
    }
    
}
