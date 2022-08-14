package lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Can't find article title on page", 15);
    }

    public String getArticleTitle() {
        return waitForTitleElement().getText();
    }

    public void swipeToFooter() {
        swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Can't find the end of the article",
                20);
    }

    public void addArticleToMyList(String nameOfFolder) {
        waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "couldn't find menu button",
                5
        );
        waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "couldn't find option Add to reading list",
                5
        );
        waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "couldn't find button Got it",
                5
        );
        waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "couldn't put text in folder input",
                5
        );

        waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                nameOfFolder,
                "couldn't put text in folder input",
                5
        );
        waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "couldn't press button OK",
                5
        );
    }

    public void closeArticle() {
        waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "couldn't press button X",
                5
        );
    }

}
