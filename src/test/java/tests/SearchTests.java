package tests;

import io.qameta.allure.*;
import lib.Platform;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

public class SearchTests extends CoreTestCase {

    @Test
    @Step("Starting test testSearchFieldText")
    @Description("Make sure that search field has text 'Search...' (only for ios and android)")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchFieldText() {
        if (!Platform.getInstance().isMW()) {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.checkSearchFieldText();
        }
    }

    @Test
    @Step("Starting test testSearch")
    @Description("Search by text")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Step("Starting test testCancelSearch")
    @Description("Click cancel search button and check that it disappeared")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    @Step("Starting test testAmountOfNotEmptySearch")
    @Description("Search by text and make sure that result isn't empty")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAmountOfNotEmptySearch() {
        String search = "Linkin Park Discography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.getAmountOfFoundArticles();

        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue("Nothing was found", amountOfSearchResults > 0);
    }

    @Test
    @Step("Starting test testAmountOfEmptySearch")
    @Description("Search by non valid text and make sure that result is empty")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        String search = "cvghjklkj";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    @Step("Starting test testSearchByWord")
    @Description("Search by word and make sure that results contain this word")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchByWord() {
        String search = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);

        searchPageObject.checkWordInResult(search);
    }

    @Test
    @Step("Starting test testCheckSearchResultByTitleAndDescription")
    @Description("Search by word and check title and description of results")
    @Feature(value = "Search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckSearchResultByTitleAndDescription() {
        String search = "python",
                firstTitle = "Python",
                firstDescription = "Wikimedia disambiguation page",
                secondTitle = "Python (programming language)",
                secondDescription = "General-purpose programming language",
                thirdTitle = "Python syntax and semantics",
                thirdDescription = "Syntax of the Python programming language";
        if (Platform.getInstance().isIOS()) {
            search = "тополь";
            firstTitle = "Тополь";
            firstDescription = "Род двудомных листопадных";
            secondTitle = "Тополь-М";
            secondDescription = "Российский ракетный комплекс";
            thirdTitle = "Тополь (ракетный комплекс)";
            thirdDescription = "Российский ракетный комплекс";
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForElementByTitleAndDescription(firstTitle, firstDescription);
        searchPageObject.waitForElementByTitleAndDescription(secondTitle, secondDescription);
        searchPageObject.waitForElementByTitleAndDescription(thirdTitle, thirdDescription);
    }
}
