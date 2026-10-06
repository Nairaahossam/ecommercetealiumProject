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
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;
import utils.TestDataResolver;

import java.util.Map;

@Epic("Tealium Ecommerce")
@Feature("User Login")
public class LoginTest extends BaseTest {

    private static final String SHEET_NAME = "LoginData";

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return ExcelReader.getTestDataByType(SHEET_NAME, "Positive");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return ExcelReader.getTestDataByType(SHEET_NAME, "Negative");
    }

    @Test(dataProvider = "validLoginData")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Validate that a user can log in with valid credentials")
    public void successfulLoginTest(Map<String, String> data) {

        AccountDashboardPage dashboardPage = new HomePage(driver)
                .openLoginPage()
                .login(
                        TestDataResolver.resolve(data.get("Email")),
                        TestDataResolver.resolve(data.get("Password"))
                );

        Assert.assertEquals(
                dashboardPage.getPageTitle().toLowerCase(),
                data.get("ExpectedMessage").toLowerCase(),
                "User is not redirected to the account dashboard"
        );
    }

    @Test(dataProvider = "invalidLoginData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate that the application shows appropriate error messages for invalid login attempts")
    public void invalidLoginTest(Map<String, String> data) {

        LoginPage loginPage = new HomePage(driver)
                .openLoginPage()
                .submitInvalidLogin(
                        TestDataResolver.resolve(data.get("Email")),
                        TestDataResolver.resolve(data.get("Password"))
                );

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                data.get("ExpectedMessage"),
                data.get("TestCaseID") + ": wrong login error message"
        );
    }
}
