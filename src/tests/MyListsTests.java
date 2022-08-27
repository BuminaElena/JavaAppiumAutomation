package tests;

import lib.Platform;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String folderName = "Learning programming";
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testSaveTwoArticles() {

        String firstSearch = "java",
                secondSearch = "python",
                firstArticleName = "Java (programming language)",
                secondArticleName = "Python (programming language)",
                folderName = "Programming learning";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(firstSearch);
        searchPageObject.clickByArticleWithSubstring(firstArticleName);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(secondSearch);
        searchPageObject.clickByArticleWithSubstring(secondArticleName);

        articlePageObject.waitForTitleElement();

        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        myListsPageObject.openFolderByName(folderName);

        myListsPageObject.swipeByArticleToDelete(firstArticleName);

        // кликаем по второй статье
        myListsPageObject.clickOnArticleWithTitle(secondArticleName);
        // получаем заголовок статьи
        String articleTitle = articlePageObject.getArticleTitle();
        // сравниваем заголовок с ожидаемым
        assertEquals("Title of article doesn't equal expected", articleTitle, secondArticleName);

    }
}
