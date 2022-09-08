package tests;

import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import lib.CoreTestCase;

public class MyListsTests extends CoreTestCase {

    private static final String
        login = "BuminaElena",
        password = "123qweQWE";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java language");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        Thread.sleep(3000); //пауза, чтобы не ловить ошибку The previously found element ""Java" StaticText" is not present
        String articleTitle = articlePageObject.getArticleTitle();
        String folderName = "Learning programming";
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            String url = driver.getCurrentUrl();
            String new_url = url.substring(0,11) + "m." + url.substring(11);
            driver.get(new_url);

            articlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle());
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testSaveTwoArticles() throws InterruptedException {

        String firstSearch = "java",
                secondSearch = "python",
                firstArticleName = "oriented programming language",
                secondArticleName = "programming language",
                folderName = "Programming learning";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(firstSearch);
        searchPageObject.clickByArticleWithSubstring(firstArticleName);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        String firstArticleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            String url = driver.getCurrentUrl();
            String new_url = url.substring(0,11) + "m." + url.substring(11);
            driver.get(new_url);

            articlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    firstArticleTitle,
                    articlePageObject.getArticleTitle());
        }
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(secondSearch);
        searchPageObject.clickByArticleWithSubstring(secondArticleName);

        articlePageObject.waitForTitleElement();
        String secondArticleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }

        myListsPageObject.swipeByArticleToDelete(firstArticleTitle);

        // проверяем, что в списке сохраненных есть статья с нужным заголовком
        myListsPageObject.waitForArticleToAppearByTitle(secondArticleTitle);
    }
}
