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

public class MainPageObject {

    private static final String
        ELEMENT_CONTAINS_TEXT_TPL = "//*[contains(@text, '{TEXT}')]";

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    /* TEMPLATES METHODS */
    protected static String getElementContainsTextXpath(String text)
    {
        return ELEMENT_CONTAINS_TEXT_TPL.replace("{TEXT}", text);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForElementPresent(By by, String error_msg, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_msg + "/n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by)
    {
        String error_msg = "Web element with locator " + by + " is not found";
        return waitForElementPresent(by, error_msg, 5);
    }

    public boolean waitForElementNotPresent(By by, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void assertElementNotPresent(By by, String error_msg)
    {
        if (!waitForElementNotPresent(by, 10))
        {
            String default_msg = "An element " + by.toString() + " supposed to be not present";
            throw new AssertionError(default_msg + " " + error_msg);
        }
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(By by, String error_message)
    {
        return waitForElementAndClick(by, error_message, 10);
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementText(By by, String expected_text, String error_message)
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
}
