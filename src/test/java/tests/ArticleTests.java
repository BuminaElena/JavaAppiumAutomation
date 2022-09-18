package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java Object-oriented programming language' and make sure that title is 'Java (programming language)'")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Unexpected title",
                "Java (programming language)",
                articleTitle
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article")})
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();

    }

    @Test
    @Features(value = {@Feature(value = "Search"),@Feature(value = "Article")})
    @DisplayName("Check that article has a title")
    @Description("We open an article and make sure that it has a title")
    @Step("Starting test testCheckArticleHasTitle")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckArticleHasTitle() {
        String search = "Java",
                articleName = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.clickByArticleWithSubstring(articleName);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.checkArticleHasTitle();
    }

}
