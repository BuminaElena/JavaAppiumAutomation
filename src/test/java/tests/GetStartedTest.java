package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    @Step("Starting test testPassThroughWelcome")
    @Description("Pass through welcome page (only for ios)")
    @Feature(value = "StartingPage")
    public void testPassThroughWelcome(){
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            return;
        }

        WelcomePageObject welcomePage = new WelcomePageObject(driver);
        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextButton();

        welcomePage.waitForNewWayToExploreText();
        welcomePage.clickNextButton();

        welcomePage.waitForAddOrEditPreferredLangText();
        welcomePage.clickNextButton();

        welcomePage.waitForLearnMoreAboutDataCollectedText();
        welcomePage.clickGetStartedButton();
    }
}
