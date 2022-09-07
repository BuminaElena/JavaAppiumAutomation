package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IosNavigationUI extends NavigationUI {
    public IosNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        MY_LISTS_LINK = "id:Сохранено";
        CLOSE_SYNCHRONIZE_LISTS_BUTTON = "id:Закрыть";
    }
}
