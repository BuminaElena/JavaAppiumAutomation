package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class='wikidata-description'][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_TITLE_ELEMENT = "css:ul.page-list>li.page-summary strong";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
//        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']/parent::*";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
