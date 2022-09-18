package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{

    private static final String
        LOGIN_BUTTON="xpath://div/a[text()='Log in']",
        LOGIN_INPUT="css:input[name='wpName']",
        PASSWORD_INPUT="css:input[name='wpPassword']",
        SUBMIT_BUTTON="css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Click auth button")
    public void clickAuthButton() {
        waitForElementPresent(LOGIN_BUTTON, "Can't find auth button", 5);
        waitForElementAndClick(LOGIN_BUTTON, "Can't find and click auth button", 5);
    }

    @Step("Enter login data")
    public void enterLoginData(String login, String password) {
        waitForElementAndSendKeys(LOGIN_INPUT, login, "Can't find and put login to the login input", 5);
        waitForElementAndSendKeys(PASSWORD_INPUT, password, "Can't find and put password to the password input", 5);
    }

    @Step("Submit auth form")
    public void submitForm() {
        waitForElementAndClick(SUBMIT_BUTTON, "Can't find and click submit button", 5);
    }
}
