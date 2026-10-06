package listeners;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot(result, "Screenshot on success - ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot(result, "Screenshot on failure - ");
    }

    private void attachScreenshot(ITestResult result, String title) {

        if (!(result.getInstance() instanceof BaseTest test)) {
            return;
        }

        WebDriver driver = test.getDriver();

        if (driver == null) {
            return;
        }

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(
                title + result.getName(),
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
        );
    }
}
