package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".category-title h1, .page-title h1")
    private WebElement categoryHeader;
    @FindBy(css = "select[title='Sort By']")
    private WebElement sortByDropdown;

    private final By productPrices = By.cssSelector(
            ".products-grid .price-box .regular-price .price, "
                    + ".products-grid .price-box .special-price .price"
    );

    public String getCategoryHeader() {
        return getText(categoryHeader);
    }

    @Step("Sort products by '{sortOption}' in ascending order")
    public CategoryPage sortBy(String sortOption) {
        selectAndWaitForNavigation(sortByDropdown, sortOption);
        return new CategoryPage(driver);
    }

    public List<Double> getProductPrices() {
        return getTexts(productPrices)
                .stream()
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .toList();
    }

    @Step("Open the '{productName}' product details")
    public ProductDetailsPage openProduct(String productName) {
        clickAndWaitForNavigation(By.xpath("//h2[@class='product-name']/a[normalize-space()=\""
                + productName + "\"]"));
        return new ProductDetailsPage(driver);
    }
}
