package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON_TPL;

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON_TPL.replace("{TITLE}", articleTitle);
    }
    /* TEMPLATES METHODS */

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Open folder '{nameOfFolder}'")
    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        waitForElementAndClick(
                folderNameXpath,
                "Can't find folder by name " + nameOfFolder,
                15
        );
    }

    @Step("Make sure that article '{articleTitle}' disappeared")
    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementNotPresent(
                articleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    @Step("Make sure that article '{articleTitle}' is in saved")
    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementPresent(
                articleXpath,
                "Can't find saved article by title " + articleTitle,
                15
        );
    }

    @Step("Swipe article '{articleTitle}' to delete")
    public void swipeByArticleToDelete(String articleTitle) throws InterruptedException {
        waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle) + "/.."; //ищем xpath родительского элемента
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            swipeElementToLeft(
                    articleXpath,
                    "Can't swipe saved article"
            );
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            Thread.sleep(2000);
            waitForElementAndClick(
                    removeLocator,
                    "Can't click button to remove article from saved",
                    10);
        }

        if (Platform.getInstance().isIOS()) {
            clickElementToTheRightUpperCorner(articleXpath, "Can't find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        waitForArticleToDisappearByTitle(articleTitle);
    }

    @Step("Click on saved article with title '{articleTitle}'")
    public void clickOnArticleWithTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        waitForElementAndClick(
                articleXpath,
                "Can't find and click on article with " + articleTitle,
                5
        );
    }

}
