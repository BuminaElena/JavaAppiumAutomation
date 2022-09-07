package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "xpath://XCUIElementTypeStaticText[@name='Узнать подробнее о Википедии']",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:Новые способы изучения",
            STEP_ADD_OR_EDIT_LANG_LINK = "xpath://XCUIElementTypeStaticText[@name='Добавить или изменить предпочтительные языки']",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "xpath://XCUIElementTypeButton[@name='Узнать подробнее о сборе данных']",
            NEXT_LINK = "xpath://XCUIElementTypeButton[@name='Далее']",
            GET_STARTED_BUTTON = "xpath://XCUIElementTypeStaticText[@name=\"Начать\"]",
            SKIP = "id:Пропустить"        ;


    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        waitForElementPresent(STEP_LEARN_MORE_LINK, "Can't find 'Learn more about Wikipedia' link",10);
    }

    public void waitForNewWayToExploreText() {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Can't find 'New ways to explore' text",10);
    }

    public void waitForAddOrEditPreferredLangText() {
        waitForElementPresent(STEP_ADD_OR_EDIT_LANG_LINK, "Can't find 'Add or edit preferred languages' text",10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Learn more about data collected' text",10);
    }

    public void clickNextButton() {
        waitForElementAndClick(NEXT_LINK, "Can't find Next button",10);
    }

    public void clickGetStartedButton() {
        waitForElementAndClick(GET_STARTED_BUTTON, "Can't find Get started button",5);
    }

    public void clickSkip() {
        waitForElementAndClick(SKIP, "Can't find skip button",5);
    }
}
