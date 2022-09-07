package lib.ui;

import lib.Platform;
import lib.ui.factories.MyListsPageObjectFactory;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,

            CANCEL_SEARCH_BUTTON;



    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return waitForElementPresent(TITLE, "Can't find article title on page", 15);
    }

    public String getArticleTitle() {
        if (Platform.getInstance().isAndroid()) {
            return waitForTitleElement().getText();
        } else {
            return waitForTitleElement().getAttribute("name");
        }

    }

    public void swipeToFooter() {
        if (Platform.getInstance().isIOS()) {
            swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can't find the end of the article",
                    40);
        } else {
            swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Can't find the end of the article",
                    40);
        }
    }

    public void addArticleToMyList(String nameOfFolder) {
        waitForElementAndClick(
                OPTIONS_BUTTON,
                "couldn't find menu button",
                5
        );
        waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "couldn't find option Add to reading list",
                5
        );
        // если папки еще не существует
        if (getAmountOfElements(ADD_TO_MY_LIST_OVERLAY) == 1) {
            waitForElementAndClick(
                    ADD_TO_MY_LIST_OVERLAY,
                    "couldn't find button Got it",
                    5
            );
            waitForElementAndClear(
                    MY_LIST_NAME_INPUT,
                    "couldn't put text in folder input",
                    5
            );
            waitForElementAndSendKeys(
                    MY_LIST_NAME_INPUT,
                    nameOfFolder,
                    "couldn't put text in folder input",
                    5
            );
            waitForElementAndClick(
                    MY_LIST_OK_BUTTON,
                    "couldn't press button OK",
                    5
            );
        } else
        //если папка уже существует
        {
            MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
            myListsPageObject.openFolderByName(nameOfFolder);
        }
    }

    public void closeArticle() {
        waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "couldn't press button X",
                5
        );
        if (Platform.getInstance().isIOS()) {
            waitForElementAndClick(
                    CANCEL_SEARCH_BUTTON,
                    "Can't click button Cancel",
                    5);
        }
    }

    public void checkArticleHasTitle() {
        assertElementPresent(
                TITLE,
                "Couldn't find article's title"
        );
    }

    public void addArticlesToMySaved() {
        waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Can't find option to add article to list", 5);
    }

}
