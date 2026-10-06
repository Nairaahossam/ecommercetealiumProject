package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a.skip-account")
    private WebElement accountMenu;
    @FindBy(css = "#header-account a[title='Register']")
    private WebElement registerLink;
    @FindBy(css = "#header-account a[title='Log In']")
    private WebElement loginLink;

    @Step("Open the Registration page from the Account menu")
    public RegistrationPage openRegistrationPage() {
        click(accountMenu);
        click(registerLink);
        return new RegistrationPage(driver);
    }

    @Step("Open the Login page from the Account menu")
    public LoginPage openLoginPage() {
        click(accountMenu);
        click(loginLink);
        return new LoginPage(driver);
    }
}
