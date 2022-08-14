package tests;

import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String folderName = "Learning programming";
        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testSaveTwoArticles() {

        String firstSearch = "java",
                secondSearch = "python",
                firstArticleName = "Java (programming language)",
                secondArticleName = "Python (programming language)",
                folderName = "Programming learning";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(firstSearch);
        searchPageObject.clickByArticleWithSubstring(firstArticleName);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.waitForTitleElement();

        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(secondSearch);
        searchPageObject.clickByArticleWithSubstring(secondArticleName);

        articlePageObject.waitForTitleElement();

        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
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
