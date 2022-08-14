package tests;

import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearchFieldText() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.checkSearchFieldText();
    }

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search = "Linkin Park Discography";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.getAmountOfFoundArticles();

        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();
        assertTrue("Nothing was found", amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String search = "cvghjklkj";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testSearchByWord() {
        String search = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);

        searchPageObject.checkWordInResult(search);
    }

    @Test
    public void testCheckSearchResultByTitleAndDescription() {
        String search = "python",
                firstTitle = "Python",
                firstDescription = "Wikimedia disambiguation page",
                secondTitle = "Python (programming language)",
                secondDescription = "General-purpose programming language",
                thirdTitle = "Python syntax and semantics",
                thirdDescription = "Syntax of the Python programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForElementByTitleAndDescription(firstTitle, firstDescription);
        searchPageObject.waitForElementByTitleAndDescription(secondTitle, secondDescription);
        searchPageObject.waitForElementByTitleAndDescription(thirdTitle, thirdDescription);
    }
}
