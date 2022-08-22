package lib.ui;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return waitForElementPresent("id:org.wikipedia:id/view_page_title_text", "Can't find article title on page", 15);
    }

    public String getArticleTitle() {
        return waitForTitleElement().getText();
    }

    public void swipeToFooter() {
        swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Can't find the end of the article",
                20);
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
            MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
            myListsPageObject.openFolderByName(nameOfFolder);
        }
    }

    public void closeArticle() {
        waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "couldn't press button X",
                5
        );
    }

    public void checkArticleHasTitle() {
        assertElementPresent(
                CLOSE_ARTICLE_BUTTON,
                "Couldn't find article's title"
        );
    }

}
