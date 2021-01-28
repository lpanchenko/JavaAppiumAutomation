package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_READING_LIST = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        READING_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        READING_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        READING_LIST_TPL = "//*[contains(@text, '{SUBSTRING}')][@resource-id='org.wikipedia:id/item_title']";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getReadingListXpath(String substring)
    {
        return READING_LIST_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForArticleTitle()
    {
        return this.waitForElementPresent(
                By.id(TITLE)
        );
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForArticleTitle();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToReadingList(String name_of_folder) throws InterruptedException {
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                10
        );

        Thread.sleep(2000);

        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_READING_LIST),
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay"
        );

        this.waitForElementAndClear(By.id(READING_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder",
                5);


        this.waitForElementAndSendKeys(By.id(READING_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5);

        this.waitForElementAndClick(By.xpath(READING_LIST_OK_BUTTON),
                "Cannot press OK button"
        );
    }

    public void addArticleToAlreadyExistedReadingList(String name_of_folder) throws InterruptedException
    {
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options"
        );

        Thread.sleep(2000);

        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_READING_LIST),
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(By.xpath(getReadingListXpath(name_of_folder)),
                "Cannot found folder " + name_of_folder + " in reading lists"
        );

        Thread.sleep(2000);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link"
        );
    }
}
