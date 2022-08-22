package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject{

    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }
    /* TEMPLATES METHODS */

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        waitForElementAndClick(
                folderNameXpath,
                "Can't find folder by name " + nameOfFolder,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementNotPresent(
                articleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementPresent(
                articleXpath,
                "Can't find saved article by title " + articleTitle,
                15
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        swipeElementToLeft(
                articleXpath,
                "Can't swipe saved article"
        );
        waitForArticleToDisappearByTitle(articleTitle);
    }

    public void clickOnArticleWithTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementAndClick(
                articleXpath,
                "Can't find and click on article with " + articleTitle,
                5
        );
    }
}
