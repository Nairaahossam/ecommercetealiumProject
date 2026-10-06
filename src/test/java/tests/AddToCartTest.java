package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountDashboardPage;
import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import utils.ExcelReader;
import utils.TestDataResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Epic("Tealium Ecommerce")
@Feature("Add Product from Accessories to Shopping Cart")
public class AddToCartTest extends BaseTest {

    private static final String SHEET_NAME = "CartData";

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        return ExcelReader.getTestDataByType(SHEET_NAME, "Positive");
    }

    @Test(dataProvider = "cartData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate that a logged-in user can add shoes from Accessories > Shoes to the shopping cart")
    public void addShoesToCartTest(Map<String, String> data) {

        String category = data.get("Category");
        String subCategory = data.get("SubCategory");
        String productName = data.get("ProductName");
        String color = data.get("Color");
        String size = data.get("Size");

        AccountDashboardPage dashboardPage = new HomePage(driver)
                .openLoginPage()
                .login(
                        TestDataResolver.resolve(data.get("Email")),
                        TestDataResolver.resolve(data.get("Password"))
                );
        Assert.assertTrue(
                dashboardPage.getPageTitle().equalsIgnoreCase(data.get("ExpectedDashboardTitle")),
                "User is not redirected to the account dashboard"
        );

        dashboardPage.hoverOverCategory(category);
        Assert.assertTrue(
                dashboardPage.isCategoryDropdownDisplayed(category),
                category + " dropdown menu is not displayed"
        );

        CategoryPage categoryPage = dashboardPage.selectSubCategory(category, subCategory);
        Assert.assertTrue(
                categoryPage.getCategoryHeader().equalsIgnoreCase(subCategory),
                "User is not redirected to the " + subCategory + " page"
        );

        categoryPage = categoryPage.sortBy(data.get("SortBy"));
        List<Double> actualPrices = categoryPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        Assert.assertEquals(
                actualPrices,
                expectedPrices,
                "Products are not sorted by price in ascending order"
        );

        ProductDetailsPage productPage = categoryPage.openProduct(productName);
        Assert.assertTrue(
                productPage.getProductName().equalsIgnoreCase(productName),
                "User is not redirected to the " + productName + " details page"
        );

        productPage.selectColor(color).selectSize(size);
        Assert.assertTrue(productPage.isColorSelected(color), "Color " + color + " is not highlighted");
        Assert.assertTrue(productPage.isSizeSelected(size), "Size " + size + " is not highlighted");

        CartPage cartPage = productPage.addToCart();
        Assert.assertTrue(
                cartPage.getPageTitle().equalsIgnoreCase(data.get("ExpectedCartTitle")),
                "User is not redirected to the shopping cart"
        );
        Assert.assertEquals(
                cartPage.getSuccessMessage(),
                data.get("ExpectedMessage"),
                "Add to cart confirmation message is wrong"
        );
    }
}
