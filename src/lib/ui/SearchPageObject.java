package lib.ui;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[@text='Search Wikipedia']",
            SEARCH_INPUT = "//*[@text='Search…']",
            SEARCH_CANCEL_BUTTON = "search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_RESULT_TITLE_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_empty_text']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']/parent::*";

    public SearchPageObject(AppiumDriver driver) {
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

    public void initSearchInput() {
        waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Can't click search init element", 5);
        waitForElementPresent(By.xpath(SEARCH_INPUT), "Can't find search input element after clicking search init element", 5);
    }

    public void waitForCancelButtonToAppear() {
        waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Can't find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Can't find and click search cancel button", 5);
    }

    public void typeSearchLine(String searchLine) {
        waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Can't find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getSearchResultString(substring);
        waitForElementPresent(By.xpath(searchResultXpath), "Can't find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getSearchResultString(substring);
        waitForElementAndClick(By.xpath(searchResultXpath), "Can't find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles() {
        waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "can't find articles by the request",
                15
        );
        return getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Can't find empty result label",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {
        assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results"
        );
    }

    public List<WebElement> getListOfFoundArticlesTitles() {
        return waitForListOfElementsPresent(
                By.xpath(SEARCH_RESULT_TITLE_ELEMENT),
                "can't find articles by the request",
                15
        );
    }

    public void checkSearchFieldText() {
        assertElementHasText(
                By.xpath(SEARCH_INPUT),
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
                By.xpath(searchResultXpath),
                "Can't find search result with title " + title + " and description " + description,
                10);
    }
}
