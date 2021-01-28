package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.waitForArticleWithDescription("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelButton();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_line = "Linkin Park Discography";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfArticles();

        assertTrue(
                "We found a few results instead of one",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String search_line = "fsdfsdfsdfsd";
        SearchPageObject.enterDataToSearchInput(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCompareSearchInputText()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.assertDefaultValueInSearchInput();
    }

    @Test
    public void testCompareSearchResult() {
        String inputData = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        int amount_of_search_results = SearchPageObject.getAmountOfArticles();
        assertTrue(
                "No data found by input value " + inputData,
                amount_of_search_results > 0
        );

        SearchPageObject.clickCancelButton();

        SearchPageObject.waitForResultIsEmpty();
    }

    @Test
    public void testCheckResultsContainSearchData() throws InterruptedException {
        String inputData = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(inputData);

        assertTrue(
                "No data found by input value " + inputData,
                SearchPageObject.eachElementContainsText(inputData)
        );
    }
}
