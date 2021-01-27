package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public void assertElementPresent(By by, String error_message)
    {
        Assert.assertNotNull(
                error_message,
                driver.findElement(by)
        );
    }

    // This method wait 10 seconds until appeared the first element by the specified locator
    // After that finds element and returns it
    public WebElement waitAndFindElement(By by, String error_msg) throws Exception {
        if (!waitUntilElementsPresent(by))
            throw new NoSuchElementException(error_msg);

        return driver.findElement(by);
    }

    public WebElement waitAndFindElement(WebElement parent, By by, String error_msg)
    {
        if (!waitUntilElementsPresent(by))
            throw new NoSuchElementException(error_msg);

        return parent.findElement(by);
    }

    public WebElement waitForElementPresent(By by, String error_msg, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_msg + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean eachElementContainsText(By by, String text)
    {
        var list = waitAndGetElements(by);
        for (WebElement w: list) {
            if ( w.findElement(By.xpath("//*[contains(@text, '" + text + "')]")) == null)
                return false;
        }
        return true;
    }

    public int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public List<WebElement> waitAndGetElements(By by)
    {
        waitUntilElementsPresent(by);
        return driver.findElements(by);
    }

    public boolean waitUntilElementsPresent(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElements(by).size() > 0);
    }

    public boolean waitUntilNoElementsPresent(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElements(by).size() == 0);
    }

    public void assetElementNotPresent(By by, String error_msg)
    {
        int amountOfElement = getAmountOfElements(by);
        if (amountOfElement > 0)
        {
            String default_msg = "An element " + by.toString() + " supposed to be not present";
            throw new AssertionError(default_msg + " " + error_msg);
        }
    }

    public WebElement waitForElementPresent(By by, String error_msg)
    {
        return waitForElementPresent(by, error_msg, 5);
    }

    public WebElement waitForElementPresent(By by)
    {
        String error_msg = "Web element with locator " + by + " is not found";
        return waitForElementPresent(by, error_msg, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message)
    {
        return waitForElementAndClick(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message)
    {
        return waitForElementAndSendKeys(by, value, error_message, 5);
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String expected_text, String error_message)
    {
        WebElement element = waitForElementPresent(by);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    }

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int)(size.width / 2);
        int start_Y = (int)(size.height * 0.8);
        int end_Y = (int)(size.height * 0.2);
        action.press(x, start_Y).waitAction(timeOfSwipe).moveTo(x, end_Y).release().perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes)
            {
                waitForElementPresent(by,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middleY = (upper_y + lower_y)/2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middleY)
                .waitAction(300)
                .moveTo(left_x, middleY)
                .release()
                .perform();
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_msg, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_msg, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
