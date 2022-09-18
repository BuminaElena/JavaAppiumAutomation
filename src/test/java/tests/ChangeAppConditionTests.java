package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Step;
import lib.Platform;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Step("Starting test testChangeScreenOrientationOnSearchResults")
    @Description("Change screen orientation and make sure that article's title is the same")
    @Features(value = {@Feature(value = "Search"),@Feature(value = "AppCondition"),@Feature(value = "Article")})
    public void testChangeScreenOrientationOnSearchResults() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        String search = "java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        String titleBeforeRotation = articlePageObject.getArticleTitle();
        rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();
        Assert.assertEquals("Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

        rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();
        Assert.assertEquals("Article title has been changed after rotation", titleAfterRotation, titleAfterSecondRotation);

    }

    @Test
    @Step("Starting test testCheckSearchArticleInBackground")
    @Description("Send app to background and make sure that search result is the same")
    @Features(value = {@Feature(value = "Search"),@Feature(value = "AppCondition")})
    public void testCheckSearchArticleInBackground() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        backgroundApp(2);

        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }


}
