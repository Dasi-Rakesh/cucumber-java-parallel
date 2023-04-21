package cucumber.steps;

import org.openqa.selenium.WebDriver;
import pages.SearchPage;
import pages.HomePage;

public class BaseSteps {
    protected HomePage homePage;
    protected SearchPage searchPage;

    public void setupScreens(WebDriver driver) {
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
    }
}