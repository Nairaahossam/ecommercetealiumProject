package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver){
        super(driver);
    }
    @FindBy(id = "firstname")
    private WebElement firstNameField;
    @FindBy(id = "lastname")
    private WebElement lastNameField;
    @FindBy(id = "email_address")
    private WebElement emailAddressField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "confirmation")
    private WebElement confirmPasswordField;
    @FindBy(css = "button[title='Register']")
    private WebElement registerButton;
    @FindBy(id = "advice-required-entry-firstname")
    private WebElement firstNameError;
    @FindBy(id = "advice-required-entry-lastname")
    private WebElement lastNameError;
    @FindBy(id = "advice-required-entry-email_address")
    private WebElement emailAddressError;
    @FindBy(id = "advice-required-entry-password")
    private WebElement passwordError;
    @FindBy(id = "advice-required-entry-confirmation")
    private WebElement confirmPasswordError;

    @Step("Register with valid data: {firstName} {lastName}, {email}")
    public AccountDashboardPage register(String firstName, String lastName, String email, String password, String confirmPassword) {
        fillForm(firstName, lastName, email, password, confirmPassword);
        clickAndWaitForNavigation(registerButton);
        return new AccountDashboardPage(driver);
    }

    @Step("Submit registration form with invalid data: {firstName} {lastName}, {email}")
    public RegistrationPage submitInvalidRegistration(String firstName, String lastName, String email, String password, String confirmPassword) {
        fillForm(firstName, lastName, email, password, confirmPassword);
        click(registerButton);
        return this;
    }

    public List<String> getRequiredFieldErrorMessages() {
        return List.of(
                getText(firstNameError),
                getText(lastNameError),
                getText(emailAddressError),
                getText(passwordError),
                getText(confirmPasswordError)
        );
    }

    private void fillForm(String firstName, String lastName, String email, String password, String confirmPassword) {

        enterText(firstNameField, firstName);
        enterText(lastNameField, lastName);
        enterText(emailAddressField, email);
        enterText(passwordField, password);
        enterText(confirmPasswordField, confirmPassword);
    }
}
