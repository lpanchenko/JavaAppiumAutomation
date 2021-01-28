package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleTitle();

        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";

        ArticlePageObject.addArticleToReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openReadingListByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testRemoveArticleFromSavedList() throws Exception {

        var firstArticle = "Persian cat";
        var secondArticle = "Turkish Angora";
        String name_of_folder = "Favorite breeds";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(firstArticle);
        SearchPageObject.clickByArticleWithTitle(firstArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.addArticleToReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.enterDataToSearchInput(secondArticle);
        SearchPageObject.clickByArticleWithTitle(secondArticle);

        ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleTitle();
        ArticlePageObject.addArticleToAlreadyExistedReadingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openReadingListByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(firstArticle);
        MyListsPageObject.waitForArticleToDisappearByTitle(firstArticle);
        MyListsPageObject.openArticle(secondArticle);

        ArticlePageObject = new ArticlePageObject(driver);
        assertEquals( "We see unexpected title",
                secondArticle,
                ArticlePageObject.getArticleTitle()
        );
    }
}
