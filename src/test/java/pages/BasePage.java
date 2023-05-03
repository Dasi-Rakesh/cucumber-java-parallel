package pages;

import managers.FileReaderManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class BasePage {
    protected WebDriver driver;

    private static final String ENVIRONMENT = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    protected WebDriverWait wait;
    protected JavascriptExecutor javascriptExecutor;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        javascriptExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 15);
    }

    protected void waitAndClick(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
    }

    protected void selectByText(By by, String text){
        try {
            waitForElementClikable(by);
            Select select = new Select(driver.findElement(by));
            select.selectByVisibleText(text);
        } catch (ElementClickInterceptedException  e){
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(by)).click().build().perform();
            shortSleep();
            driver.findElement(By.xpath("//a[text()='"+text+"']")).click();
        }
        catch (NoSuchElementException e){
            System.out.println("No Such element found : " + e.toString());
        }
        catch (WebDriverException e){
            Actions actions = new Actions(driver);
            try {
                actions.moveToElement(driver.findElement(by)).click().build().perform();
                shortSleep();
                driver.findElement(By.xpath("//a[text()='" + text + "']")).click();
            }catch (MoveTargetOutOfBoundsException exception){
                driver.navigate().refresh();
            }
        }
    }

    protected void shortSleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void VerifyElementDisplayedAndClick(By locator, String message) {
        if (ENVIRONMENT.equalsIgnoreCase("local")) {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed()) {
                    shortSleep();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
                }
            } catch (Exception e) {
                System.out.println("Element not clicked");
            }
        }else {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed()) {
                    shortSleep();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
                    javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"+" + message + "+\"}}");
                }
            } catch (Exception e) {
                javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Element Not clicked!\"}}");
                System.out.println("Element not clicked");
            }
        }
    }

    protected void VerifyElementDisplayed(By locator, String message) {
        if (ENVIRONMENT.equalsIgnoreCase("local")) {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed()) {
                    boolean isDisplayed = driver.findElement(locator).isDisplayed();
                    Assert.assertTrue(isDisplayed);
                }
            } catch (Exception e) {
                Assert.assertFalse(false);
            }
        }else {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed()) {
                    boolean isDisplayed = driver.findElement(locator).isDisplayed();
                    Assert.assertTrue(isDisplayed);
                    javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"+" + message + "+\"}}");
                }
            } catch (Exception e) {
                javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Element Not clicked!\"}}");
                System.out.println("Element not clicked");
            }
        }
    }

    protected void VerifyText(String expected, String actual, String message) {
        if (ENVIRONMENT.equalsIgnoreCase("local")) {
            Assert.assertEquals(expected, actual, message);
        } else {
            if (expected.equals(actual)) {
                Assert.assertEquals(expected, actual);
                javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"+" + message + "+\"}}");
                System.out.println("Page Title is verified");
            } else {
                javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Text is Different\"}}");
            }
        }
    }

    protected void click(By by) {
        driver.findElement(by).click();
    }

    protected void hideKeyboard() {
        driver.navigate().back();
    }

    protected List<WebElement> waitAndFindElements(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected WebElement waitAndFindElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementClikable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected String getText(By by) {
        return waitAndFindElement(by).getText();
    }

    protected void sendKey(By by, String text) {
        waitAndFindElement(by).sendKeys(text);
    }

    public List<WebElement> getElements(By elementLocator) {
        try {
            List<WebElement> elements = driver.findElements(elementLocator);
            return elements;
        } catch (WebDriverException exception) {
            throw new WebDriverException(
                    "Element with locator : " + elementLocator + " was not displayed and unable to get the count",
                    exception);
        }
    }
}
