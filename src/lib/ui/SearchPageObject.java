package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
        SEARCH_INPUT_DEFAULT_TEXT = "org.wikipedia:id/search_src_text",

        SEARCH_RESULT_ARTICLE = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_RESULT_ARTICLE_DESCRIPTION_TPL = "//*[@text='{SUBSTRING}'][@resource-id='org.wikipedia:id/page_list_item_description']",
        SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL = "//*[contains(@text, '{SUBSTRING}')][@resource-id='org.wikipedia:id/page_list_item_description']",
        SEARCH_RESULT_ARTICLE_TITLE_TPL = "//*[@text='{SUBSTRING}'][@resource-id='org.wikipedia:id/page_list_item_title']",
        SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL = "//*[contains(@text,'{SUBSTRING}')][@resource-id='org.wikipedia:id/page_list_item_title']",
        SEARCH_RESULT_NO_RESULTS_FOUND = "//*[@text='No results found']",
        SEARCH_RESULT_EMPTY_CONTAINER = "org.wikipedia:id/search_empty_container",

        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getArticleWithTitleXpath(String substring)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithTitleContainsTextXpath(String substring)
    {
        return SEARCH_RESULT_ARTICLE_TITLE_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithDescriptionXpath(String substring)
    {
        return SEARCH_RESULT_ARTICLE_DESCRIPTION_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getArticleWithDescriptionContainsTextXpath(String substring)
    {
        return SEARCH_RESULT_ARTICLE_DESCRIPTION_CONTAINS_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element");

        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking on search init element",
                10);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search Cancel button",
                5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON),
                5);
    }

    public void clickCancelButton()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button");
    }

    public void enterDataToSearchInput(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5);
    }

    public void waitForArticleWithDescription(String description)
    {
        String search_result_xpath = getArticleWithDescriptionXpath(description);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Cannot find search result with substring " + description,
                5);
    }

    public void waitForArticleContainsDescription(String substring)
    {
        String search_result_xpath = getArticleWithDescriptionContainsTextXpath(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring,
                5);
    }

    public void clickByArticleWithDescription(String substring)
    {
        String search_result_xpath = getArticleWithDescriptionXpath(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),
                "Cannot find and click search result with substring" + substring);
    }

    public void clickByArticleWithTitle(String substring)
    {
        String search_result_xpath = getArticleWithTitleXpath(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath),
                "Cannot find and click search result with substring" + substring);
    }

    public int getAmountOfArticles()
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ARTICLE),
                "Cannot find anything by request ",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ARTICLE));
    }

    public int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_NO_RESULTS_FOUND),
                "Cannot find empty result element",
                15
        );
    }

    public void waitForResultIsEmpty()
    {
        waitForElementPresent(By.id(SEARCH_RESULT_EMPTY_CONTAINER),
                "Search result should be empty",
            5);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ARTICLE),
                "We supposed not to find any results");
    }

    public void assertDefaultValueInSearchInput()
    {
        this.assertElementText(
                By.id(SEARCH_INPUT_DEFAULT_TEXT),
                "Search…",
                "Search Input contains unexpected text"
        );
    }

    public boolean eachElementContainsText(String text) throws InterruptedException
    {
        String search_result_xpath = getArticleWithTitleContainsTextXpath(text);
        waitForElementPresent(By.xpath(search_result_xpath));

        String element_contains_text_xpath = this.getElementContainsTextXpath(text);
        // Receive all elements in search result
        List<WebElement> list = driver.findElements(By.xpath(search_result_xpath));

        // Check if each element contains text
        for (WebElement w: list) {
            if (!w.getAttribute("text").contains(text))
                return false;
        }
        return true;
    }
}