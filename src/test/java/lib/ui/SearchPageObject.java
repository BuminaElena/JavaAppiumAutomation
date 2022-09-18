package lib.ui;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_RESULT_TITLE_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getSearchResultString(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchResultByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    @Step("Initializing the search field")
    public void initSearchInput() {
        waitForElementAndClick(SEARCH_INIT_ELEMENT, "Can't click search init element", 5);
        waitForElementPresent(SEARCH_INPUT, "Can't find search input element after clicking search init element", 5);
    }

    @Step("Wait for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        waitForElementPresent(SEARCH_CANCEL_BUTTON, "Can't find search cancel button", 5);
    }

    @Step("Wait for cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Click button to cancel search result")
    public void clickCancelSearch() {
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Can't find and click search cancel button", 5);
    }

    @Step("Typing '{searchLine}' to the search line")
    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Can't find and type into search input", 5);
    }

    @Step("Wait for search result")
    public void waitForSearchResult(String substring) {
        String searchResultXpath = getSearchResultString(substring);
        waitForElementPresent(searchResultXpath, "Can't find search result with substring " + substring);
    }

    @Step("Wait for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getSearchResultString(substring);
        waitForElementAndClick(searchResultXpath, "Can't find and click search result with substring " + substring, 15);
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles() {
        waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "can't find articles by the request",
                15
        );
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for empty result label")
    public void waitForEmptyResultsLabel() {
        waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Can't find empty result label",
                15
        );
    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoResultOfSearch() {
        assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    public List<WebElement> getListOfFoundArticlesTitles() {
        return waitForListOfElementsPresent(
                SEARCH_RESULT_TITLE_ELEMENT,
                "can't find articles by the request",
                15
        );
    }

    public void checkSearchFieldText() {
        assertElementHasText(
                SEARCH_INPUT,
                "Search…",
                "unexpected text in search field",
                5
        );
    }

    public void checkWordInResult(String word) {
        for (WebElement element : getListOfFoundArticlesTitles()) {
            String elementText = element.getText().toLowerCase();
            Assert.assertTrue(
                    "Result doesn't contains " + word + ". Result is " + elementText,
                    elementText.contains(word.toLowerCase())
            );
        }
    }

    public void  waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getSearchResultByTitleAndDescription(title, description);
        waitForElementPresent(
                searchResultXpath,
                "Can't find search result with title " + title + " and description " + description,
                10);
    }
}
