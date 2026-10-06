package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountDashboardPage extends BasePage {

    public AccountDashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".success-msg span")
    private WebElement successMessage;

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    @Step("Hover over the '{category}' category")
    public AccountDashboardPage hoverOverCategory(String category) {
        hover(categoryLink(category));
        return this;
    }

    public boolean isCategoryDropdownDisplayed(String category) {
        return isDisplayed(categoryDropdown(category));
    }

    @Step("Select the '{subCategory}' subcategory")
    public CategoryPage selectSubCategory(String category, String subCategory) {
        clickAndWaitForNavigation(By.xpath(categoryXpath(category)
                + "/ul//a[normalize-space()='" + subCategory + "']"));
        return new CategoryPage(driver);
    }

    private String categoryXpath(String category) {
        return "//ol[contains(@class,'nav-primary')]/li[a[normalize-space()='" + category + "']]";
    }

    private By categoryLink(String category) {
        return By.xpath(categoryXpath(category) + "/a");
    }

    private By categoryDropdown(String category) {
        return By.xpath(categoryXpath(category) + "/ul");
    }
}
