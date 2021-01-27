import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCompareSearchInputText()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        MainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Search Input contains unexpected text"
        );
    }

    @Test
    public void testCompareSearchResult()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String inputData = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                inputData,
                "Cannot find search input",
                5
        );

        assertTrue(
                "No data found by input value " + inputData,
                MainPageObject.waitUntilElementsPresent(
                        By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"))
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel button",
                15
        );

        assertTrue(
                "Search results should be empty after pressing cancel button",
                MainPageObject.waitUntilNoElementsPresent(
                        By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"))
        );
    }

    @Test
    public void testCheckResultsContainSearchData()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input",
                5
        );

        String searchText = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchText,
                "Cannot find search input",
                5
        );

        assertTrue(
                "No data found by input value " + searchText,
                MainPageObject.eachElementContainsText(
                        By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                        "Java")
        );
    }

    @Test
    public void testRemoveArticleFromSavedList() throws Exception {

        var firstArticle = "Persian cat";
        var secondArticle = "Turkish Angora";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                firstArticle,
                "Cannot find search input",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + firstArticle + "'][@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find 'Search wikipedia' input"
        );


        WebElement toolBar = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_toolbar"),
                "Cannot find toolbar by id"
        );

        // During receiving menu element with waitForElementPresent method
        // Script clicks twice on 'More actions item' and opened the wrong element
        // Use Find element method that works better in this case
        var moreOptionsMenuItem = MainPageObject.waitAndFindElement(
                toolBar,
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find More options menu item"
                );
        moreOptionsMenuItem.click();

        var addToReadingListMenuItem = MainPageObject.waitAndFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Can not find Add to reading list menu item");
        addToReadingListMenuItem.click();



        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot open more options"
        );

        Thread.sleep(5000);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Cannot open add to list"
        );


        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay"
        );

        MainPageObject.waitForElementAndClear(By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5);

        String name_of_folder = "Two article list";

        MainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5);

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='OK']"),
                "Cannot press OK button"
        );

        MainPageObject.waitForElementAndClick(By.xpath("//*[@content-desc=\"Search Wikipedia\"]"),
                        "Can not found 'Search Wikipedia button'");

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                secondArticle,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+secondArticle+"'][@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find 'Search wikipedia' input",
                10
        );

        toolBar = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_toolbar"),
                "Cannot find toolbar by id"
        );

        moreOptionsMenuItem = MainPageObject.waitAndFindElement(
                toolBar,
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find More options menu item"
        );
        moreOptionsMenuItem.click();

        addToReadingListMenuItem = MainPageObject.waitAndFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Can not find Add to reading list menu item");
        addToReadingListMenuItem.click();

        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='"+name_of_folder+"']"),
                "Canot found folder "+name_of_folder+" with saved articles");

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
        );

        MainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot found navigation button to My list"
        );

        var savedList = MainPageObject.waitAndFindElement(By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find saved list with name " + name_of_folder);
        savedList.click();

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='"+ firstArticle+"']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='"+firstArticle+"']"),
                "Article with name " + firstArticle + " should be deleted",
                5
        );

        MainPageObject.waitForElementAndClick(By.xpath("//*[@text='"+secondArticle+"']"),
                "Cannot find saved article with name " + secondArticle,
                5
        );

        MainPageObject.assertElementHasText(By.id("org.wikipedia:id/view_page_title_text"),
                secondArticle,
                "Article title should has name " + secondArticle);
    }

    @Test
    public void testCheckArticleTitleExistence()
    {
        var article = "Turkish Angora";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search wikipedia' input"
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                article,
                "Cannot find search input",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + article + "'][@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find 'Search wikipedia' input"
        );

        MainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"),
                "Title for article " + article + "should not be null");
    }
}
