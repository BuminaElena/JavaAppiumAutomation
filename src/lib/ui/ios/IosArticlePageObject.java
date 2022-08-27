package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IosArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java";
        FOOTER_ELEMENT = "id:Просмотреть статью в браузере";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Сохранить на потом";
        CLOSE_ARTICLE_BUTTON = "id:Поиск";
        CANCEL_SEARCH_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Отменить']";
    }

    public IosArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
