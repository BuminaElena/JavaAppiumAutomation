package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class NavigationUI extends MainPageObject{

    protected static String MY_LISTS_LINK,
            CLOSE_SYNCHRONIZE_LISTS_BUTTON;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        waitForElementAndClick(
                MY_LISTS_LINK,
                "Can't find navigation button to My lists",
                5
        );
        if (Platform.getInstance().isIOS()) {
            waitForElementAndClick(
                    CLOSE_SYNCHRONIZE_LISTS_BUTTON,
                    "Can't find close synchronize lists button",
                    5
            );
        }
    }
}
