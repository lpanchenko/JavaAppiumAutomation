package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase
{
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Appium");
        SearchPageObject.clickByArticleWithTitle("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testCheckArticleTitleExistence()
    {
        var article = "Turkish Angora";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(article);

        SearchPageObject.clickByArticleWithTitle(article);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        assertTrue("Article should not be null",
                ArticlePageObject.waitForArticleTitle() != null);
    }
}
