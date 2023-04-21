package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    By FilpKartAssuredCheckbox = By.xpath("(//input[@type='checkbox']/following::div/img[contains(@src,'flixcart')])[1]");
    By SortFeatureDropdown = By.xpath("//select[@id='s-result-sort-select']");
    By PriceHighToLow = By.xpath("//div[text()='Price -- High to Low']");

    By ProductResults = By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']");

    By ProductPrices = By.xpath("//div[@class='_30jeq3 _1_WHN1']");

    /**
     * Actions
     */
    public void SelectFlipkartAssuredCheckbox() {
        shortSleep();
        wait.until(ExpectedConditions.visibilityOfElementLocated(FilpKartAssuredCheckbox)).click();
    }

    public void SelectPriceHighToLow() {
        shortSleep();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SortFeatureDropdown));
        selectByText(SortFeatureDropdown, "Price: High to Low");
        shortSleep();
    }

    public void GetProductResults() {
        shortSleep();
        List<WebElement> products = getElements(ProductResults);
        for (int i = 0; i < products.size(); i++) {
            WebElement productName = products.get(i);
            if(productName.getText().contains("Apple iPhone")){
                System.out.println("Product Name  --> |" + productName.getText());
            }
        }
    }
}
