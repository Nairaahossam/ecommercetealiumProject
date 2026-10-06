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
import pages.RegistrationPage;
import utils.ExcelReader;
import utils.TestDataResolver;

import java.util.Map;

@Epic("Tealium Ecommerce")
@Feature("User Registration")
public class RegistrationTest extends BaseTest {

    private static final String SHEET_NAME = "RegistrationData";

    @DataProvider(name = "validRegistrationData")
    public Object[][] validRegistrationData() {
        return ExcelReader.getTestDataByType(SHEET_NAME, "Positive");
    }

    @DataProvider(name = "invalidRegistrationData")
    public Object[][] invalidRegistrationData() {
        return ExcelReader.getTestDataByType(SHEET_NAME, "Negative");
    }

    @Test(dataProvider = "validRegistrationData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate that a user can successfully register with valid credentials")
    public void successfulRegistrationTest(Map<String, String> data) {

        AccountDashboardPage dashboardPage = new HomePage(driver)
                .openRegistrationPage()
                .register(
                        data.get("FirstName"),
                        data.get("LastName"),
                        TestDataResolver.resolve(data.get("Email")),
                        data.get("Password"),
                        data.get("ConfirmPassword")
                );

        Assert.assertTrue(
                dashboardPage.getSuccessMessage().contains(data.get("ExpectedMessage")),
                "Registration success message is not displayed"
        );
    }

    @Test(dataProvider = "invalidRegistrationData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate that the application shows appropriate error messages for invalid registration inputs")
    public void invalidRegistrationTest(Map<String, String> data) {

        RegistrationPage registrationPage = new HomePage(driver)
                .openRegistrationPage()
                .submitInvalidRegistration(
                        data.get("FirstName"),
                        data.get("LastName"),
                        TestDataResolver.resolve(data.get("Email")),
                        data.get("Password"),
                        data.get("ConfirmPassword")
                );

        for (String errorMessage : registrationPage.getRequiredFieldErrorMessages()) {
            Assert.assertEquals(
                    errorMessage,
                    data.get("ExpectedMessage"),
                    data.get("TestCaseID") + ": wrong required field error message"
            );
        }
    }
}
