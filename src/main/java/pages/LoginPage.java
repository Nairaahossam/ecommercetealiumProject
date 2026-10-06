package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    private WebElement emailField;
    @FindBy(id = "pass")
    private WebElement passwordField;
    @FindBy(id = "send2")
    private WebElement loginButton;
    @FindBy(css = ".error-msg span")
    private WebElement errorMessage;

    @Step("Login with email: {email}")
    public AccountDashboardPage login(String email, String password) {
        fillForm(email, password);
        clickAndWaitForNavigation(loginButton);
        return new AccountDashboardPage(driver);
    }

    @Step("Submit login form with invalid data, email: {email}")
    public LoginPage submitInvalidLogin(String email, String password) {
        fillForm(email, password);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    private void fillForm(String email, String password) {
        enterText(emailField, email);
        enterText(passwordField, password);
    }
}
