package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String MY_LISTS_LINK,
            CLOSE_SYNCHRONIZE_LISTS_BUTTON,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            waitForElementAndClick(OPEN_NAVIGATION, "Can't find and click open navigation button", 5);
        } else {
            System.out.println("Method openNavigation does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Can't find navigation button to My lists",
                     5
            );
        } else {
            waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Can't find navigation button to My lists",
                    5
            );
        }
        if (Platform.getInstance().isIOS()) {
            waitForElementAndClick(
                    CLOSE_SYNCHRONIZE_LISTS_BUTTON,
                    "Can't find close synchronize lists button",
                    5
            );
        }
    }
}
