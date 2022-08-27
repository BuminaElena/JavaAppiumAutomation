package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class IosNavigationUI extends NavigationUI {
    public IosNavigationUI(AppiumDriver driver) {
        super(driver);
    }

    static {
        MY_LISTS_LINK = "id:Сохранено";
        CLOSE_SYNCHRONIZE_LISTS_BUTTON = "id:Закрыть";
    }
}
