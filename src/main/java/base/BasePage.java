package base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import java.time.Duration;
import java.util.List;

public class BasePage {

    private static final By CONSENT_POPUP = By.cssSelector(".privacy_prompt");
    private static final By CONSENT_OPT_OUT = By.id("privacy_pref_optout");
    private static final By CONSENT_SUBMIT = By.id("consent_prompt_submit");

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(
                Long.parseLong(ConfigReader.getProperty("explicitWait"))
        ));
        PageFactory.initElements(driver , this);

    }
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element
        );
    }

    protected void click(WebElement element) {
        waitForClickable(element);
        scrollToElement(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {

            dismissConsentPopup();
            element.click();
        }
    }

    protected void click(By locator) {
        click(wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    protected void clickAndWaitForNavigation(WebElement element) {
        WebElement currentPage = driver.findElement(By.tagName("html"));
        click(element);
        wait.until(ExpectedConditions.stalenessOf(currentPage));
    }

    protected void clickAndWaitForNavigation(By locator) {
        clickAndWaitForNavigation(wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    protected void selectAndWaitForNavigation(WebElement element, String text) {
        WebElement currentPage = driver.findElement(By.tagName("html"));
        selectByVisibleText(element, text);
        wait.until(ExpectedConditions.stalenessOf(currentPage));
    }

    protected void enterText(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected List<String> getTexts(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    protected void hover(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Actions(driver).moveToElement(element).perform();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    protected void selectByVisibleText(WebElement element, String text) {
        waitForVisibility(element);
        new Select(element).selectByVisibleText(text);
    }

    protected String getAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator))
                .getAttribute(attribute);
    }

    public void waitForAndDismissConsentPopup() {
        if (isDisplayed(CONSENT_POPUP)) {
            dismissConsentPopup();
        }
    }

    private void dismissConsentPopup() {
        if (driver.findElements(CONSENT_POPUP).stream().anyMatch(WebElement::isDisplayed)) {
            driver.findElement(CONSENT_OPT_OUT).click();
            driver.findElement(CONSENT_SUBMIT).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(CONSENT_POPUP));
        }
    }

    public String getPageTitle() {
        return getText(By.cssSelector(".page-title h1"));
    }
}
