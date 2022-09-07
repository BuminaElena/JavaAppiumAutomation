package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IosSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Поиск по Википедии']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Поиск по Википедии']";
        SEARCH_CANCEL_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Отменить']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeCell";
        SEARCH_RESULT_TITLE_ELEMENT = "xpath://XCUIElementTypeStaticText";
        SEARCH_EMPTY_RESULT_ELEMENT = "id:Ничего не найдено";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']/following-sibling::XCUIElementTypeStaticText[contains(@name,'{DESCRIPTION}')]/parent::*";
    }

    public IosSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
