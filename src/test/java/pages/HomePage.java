package pages;

import managers.FileReaderManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String URL = FileReaderManager.getInstance().getConfigReader().getURL();
    private static final String B_USERNAME = FileReaderManager.getInstance().getConfigReader().getBUserName();
    private static final String B_PASSWORD = FileReaderManager.getInstance().getConfigReader().getBPassword();

    /**
     * WebElements Elements
     */

    By closeButton = By.xpath("(//button)[2]");
    By SearchBar = By.xpath("//input[@id = 'twotabsearchtextbox']");
    By SearchIcon = By.xpath("//input[@type='submit']");
    By SignInButton = By.xpath("//a[text()='Sign In']");
    By UserNameField = By.xpath("//input[@id='react-select-2-input']");
    By PasswordField = By.xpath("//input[@id='react-select-3-input']");
    By CheckoutButton = By.xpath("//div[text()='Checkout']");
    By SeeMoreLink = By.xpath("//span[text()='Cellular Phone Operating System']/following::span[text()='See more'][1]");
    By LoginButton = By.id("login-btn");
    By FirstName = By.id("firstNameInput");
    By LastName = By.id("lastNameInput");
    By AddressField= By.id("addressLine1Input");
    By StateField =By.id("provinceInput");
    By PostalCode = By.id("postCodeInput");
    By SubmitButton = By.id("checkout-shipping-continue");
    By iOS16 = By.xpath("//li[@aria-label='iOS 16']//child::input[@type='checkbox']");
    By OrderConfirmation = By.id("confirmation-message");

    By Categories(String category) {
        return By.xpath("//span[text()='CATEGORIES']/following::a[@title='" + category + "']");
    }

    By UsernameText(String username) {
        return By.xpath("//span[text()='" + username + "']");
    }

    By CellPhoneOperatingSystem(String os) {
        return By.xpath("(//li[contains(@aria-label,'"+os+"')]//child::input[@type='checkbox'])[1]");
    }

    By ProductName(String product) {
        return By.xpath("//p[text()='"+product+"']/parent::div/child::div[text()='Add to cart']");
    }

    By BrandFilter(String filter) {
        return By.xpath("//input[@type='checkbox']/following::div[text()='" + filter + "']");
    }

    /**
     * Actions
     */
    public void navigateToWebsite(String URL) {
        driver.get("https://www." + URL + ".com");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        String title = driver.getTitle();
        System.out.println("Website Title is : " + title);
        VerifyText("", "", "Title Matched");
    }

    public void navigateToBStackDemo() {
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        String title = driver.getTitle();
        System.out.println("Website Title is : " + title);
        VerifyText("StackDemo", "StackDemo", "Title Matched");
    }

    public void signInAndVerify() {
        VerifyElementDisplayedAndClick(SignInButton, "Sign In Button is displayed");
        VerifyElementDisplayedAndClick(UserNameField, "Username Field is not displayed");
        wait.until(ExpectedConditions.elementToBeClickable(UserNameField)).sendKeys(B_USERNAME);
        wait.until(ExpectedConditions.elementToBeClickable(UserNameField)).sendKeys(Keys.ENTER);
        shortSleep();
        VerifyElementDisplayedAndClick(PasswordField, "Password Field is not displayed");
        wait.until(ExpectedConditions.visibilityOfElementLocated(PasswordField)).sendKeys(B_PASSWORD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(PasswordField)).sendKeys(Keys.ENTER);

        VerifyElementDisplayedAndClick(LoginButton, "Login Button is not displayed");
        shortSleep();
        VerifyElementDisplayed(UsernameText(B_USERNAME), "User has logged in Successfully");
    }

    public void clickOnCloseButton() {
        shortSleep();
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton)).click();
        }
    }

    public void SearchForProduct(String searchItem) {
        shortSleep();
        waitForElementClikable(SearchBar);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SearchBar)).sendKeys(searchItem);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SearchIcon)).click();
        shortSleep();
    }

    public void SelectCategory(String category) {
        shortSleep();
        VerifyElementDisplayedAndClick(Categories(category), "Element Not Displayed");
    }

    public void SelectCellPhoneOperatingSystem(String os) {
        shortSleep();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(SeeMoreLink));
        VerifyElementDisplayedAndClick(SeeMoreLink, "See More Link is clicked");
        shortSleep();
        Actions actions = new Actions(driver);
        try{
        actions.moveToElement(new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(CellPhoneOperatingSystem(os)))).build().perform();
        actions.doubleClick(driver.findElement(CellPhoneOperatingSystem(os))).build().perform();
        shortSleep();
        }catch (Exception e){
            Dimension originalSize = driver.manage().window().getSize();
            System.out.println("actual main window size is  " + originalSize);
            Dimension dimension = new Dimension(1500,1000);
            //Resize current window to the set dimension
            driver.manage().window().setSize(dimension);
            try {
                driver.findElement(CellPhoneOperatingSystem(os)).click();
            }catch (Exception exception){
                driver.navigate().refresh();
            }
        }
    }

    public void SelectBrandFilter(String filter) {
        shortSleep();
        VerifyElementDisplayedAndClick(BrandFilter(filter), "Element Not Displayed");
    }

    public void AddProduct(String product){
        VerifyElementDisplayedAndClick(ProductName(product), "Product Added");
    }

    public void CheckoutAndAddAddressDetails(String fName, String lName, String address, String state, String pincode ){
        VerifyElementDisplayedAndClick(CheckoutButton, "Clicked on Checkout button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(FirstName)).sendKeys(fName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LastName)).sendKeys(lName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AddressField)).sendKeys(address);
        wait.until(ExpectedConditions.visibilityOfElementLocated(StateField)).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(PostalCode)).sendKeys(pincode);
        VerifyElementDisplayedAndClick(SubmitButton, "Clicked on Checkout button");
        shortSleep();
    }

    public void VerifyOrderConfirmation(){
        VerifyElementDisplayed(OrderConfirmation, "Order placed successfully");
    }
}
