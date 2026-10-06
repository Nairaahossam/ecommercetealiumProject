package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage {

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    private static final String COLOR_SWATCHES = "configurable_swatch_color";
    private static final String SIZE_SWATCHES = "configurable_swatch_shoe_size";
    private static final String SELECTED_CLASS = "selected";

    @FindBy(css = ".product-shop .product-name .h1")
    private WebElement productName;
    @FindBy(css = ".add-to-cart-buttons button.btn-cart")
    private WebElement addToCartButton;

    public String getProductName() {
        return getText(productName);
    }

    @Step("Select color '{color}'")
    public ProductDetailsPage selectColor(String color) {
        click(swatchLink(COLOR_SWATCHES, color));
        return this;
    }

    @Step("Select size '{size}'")
    public ProductDetailsPage selectSize(String size) {
        click(swatchLink(SIZE_SWATCHES, size));
        return this;
    }

    public boolean isColorSelected(String color) {
        return isSwatchSelected(COLOR_SWATCHES, color);
    }

    public boolean isSizeSelected(String size) {
        return isSwatchSelected(SIZE_SWATCHES, size);
    }

    @Step("Add the product to the cart")
    public CartPage addToCart() {
        clickAndWaitForNavigation(addToCartButton);
        return new CartPage(driver);
    }

    private By swatchLink(String swatchListId, String option) {
        return By.cssSelector("#" + swatchListId + " a[title='" + option + "']");
    }

    private boolean isSwatchSelected(String swatchListId, String option) {
        String classes = getAttribute(
                By.xpath("//ul[@id='" + swatchListId + "']/li[a[@title='" + option + "']]"),
                "class"
        );
        return classes.contains(SELECTED_CLASS);
    }
}
