package base;
import drivers.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.HomePage;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.TestAccount;
import utils.TestDataResolver;

import java.time.Duration;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;

    private static final String REGISTRATION_SHEET = "RegistrationData";
    private static final String VALID_REGISTRATION_ID = "TC_REG_01";

    @BeforeSuite(alwaysRun = true)
    public void createTestAccount() {

        Map<String, String> data =
                ExcelReader.getTestCaseData(REGISTRATION_SHEET, VALID_REGISTRATION_ID);
        String email = TestDataResolver.resolve(data.get("Email"));
        String password = data.get("Password");

        WebDriver setupDriver = openBrowser();
        try {
            new HomePage(setupDriver)
                    .openRegistrationPage()
                    .register(data.get("FirstName"), data.get("LastName"),
                            email, password, data.get("ConfirmPassword"))
                    .getSuccessMessage();
        } finally {
            setupDriver.quit();
        }

        TestAccount.setCredentials(email, password);
    }

    @BeforeMethod
    public void setUp() {
        driver = openBrowser();
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebDriver openBrowser() {

        String browser = ConfigReader.getProperty("browser");
        String baseUrl = ConfigReader.getProperty("baseUrl");

        WebDriver newDriver = DriverFactory.createDriver(browser);

        newDriver.manage().window().maximize();
        newDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Long.parseLong(ConfigReader.getProperty("pageLoadTimeout"))
        ));
        openUrl(newDriver, baseUrl);
        new HomePage(newDriver).waitForAndDismissConsentPopup();
        return newDriver;
    }

    private void openUrl(WebDriver webDriver, String url) {
        try {
            webDriver.get(url);
        } catch (TimeoutException e) {
            webDriver.get(url);
        }
    }
}
