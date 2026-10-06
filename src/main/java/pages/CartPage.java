package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".success-msg span")
    private WebElement successMessage;

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}
