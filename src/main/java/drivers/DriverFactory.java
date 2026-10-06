package drivers;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
public class DriverFactory {

    private static final PageLoadStrategy PAGE_LOAD_STRATEGY = PageLoadStrategy.EAGER;

    public static WebDriver createDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PAGE_LOAD_STRATEGY);
                return new ChromeDriver(chromeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PAGE_LOAD_STRATEGY);
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PAGE_LOAD_STRATEGY);
                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
    }
}
