import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "C:/Users/elbit/Documents/Repositories/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
               By.xpath("//*[@text='Object-oriented programming language'][@resource-id='org.wikipedia:id/page_list_item_description']"),
               "Cannot found Object oriented programming language topic searching by Java",
               15
       );
    }

    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel button",
                15
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language'][@resource-id='org.wikipedia:id/page_list_item_description']"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testCompareSearchInputText()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Input contains unexpected text"
        );
    }

    @Test
    public void testCompareSearchResult()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String inputData = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                inputData,
                "Cannot find search input",
                5
        );

        Assert.assertTrue(
                "No data found by input value " + inputData,
                waitUntilElementsPresent(
                        By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"))
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel button",
                15
        );

        Assert.assertTrue(
                "Search results should be empty after pressing cancel button",
                waitUntilNoElementsPresent(
                        By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"))
        );
    }

    private WebElement waitForElementPresent(By by, String error_msg, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_msg + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private boolean waitUntilElementsPresent(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElements(by).size() > 0);
    }

    private boolean waitUntilNoElementsPresent(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElements(by).size() == 0);
    }

    private WebElement waitForElementPresent(By by, String error_msg)
    {
        return waitForElementPresent(by, error_msg, 5);
    }

    private WebElement waitForElementPresent(By by)
    {
        String error_msg = "Web element with locator " + by + " is not found";
        return waitForElementPresent(by, error_msg, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message)
    {
        WebElement element = waitForElementPresent(by);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    }
}
