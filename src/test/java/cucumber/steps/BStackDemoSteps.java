package cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.ThreadLocalDriver;

import java.util.List;
import java.util.Map;

public class BStackDemoSteps extends BaseSteps {

    @Before
    public void setupLoginSteps() {
        setupScreens(ThreadLocalDriver.getTLDriver());
    }

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        homePage.navigateToWebsite(url);
    }

    @When("I search for {string} and select {string} in Categories")
    public void iSearchForAndSelectInCategories(String product, String category) {
        homePage.SearchForProduct(product);
        homePage.SelectCategory(category);
    }

    @When("I search for {string}")
    public void iSearchFor(String product) {
        homePage.SearchForProduct(product);
    }

    @And("^I set the following filters and select Flipkart Assured$")
    public void iSetTheFollowingFiltersAndSelectFlipkartAssured(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> value : values) {
            homePage.SelectBrandFilter(value.get("Brand"));
        }
        searchPage.SelectFlipkartAssuredCheckbox();
        Thread.sleep(6000);
    }

    @And("^I sort the Price from High to Low$")
    public void iSortThePriceFromHighToLow() {
        searchPage.SelectPriceHighToLow();
    }

    @Then("^I capture all the results on the First Page$")
    public void iCaptureAllTheResultsOnTheFirstPage() {
        searchPage.GetProductResults();
    }

    @Given("I navigate to bstackdemo")
    public void iNavigateToBstackdemo() {
       homePage.navigateToBStackDemo();
    }

    @And("I SignIn with valid credentials")
    public void iSignInWithValidCredentials() {
        homePage.signInAndVerify();
    }


    @And("I select Cell Phone Operating System as {string}")
    public void iSelectCellPhoneOperatingSystemAs(String os) {
        homePage.SelectCellPhoneOperatingSystem(os);
    }

    @And("I add {string} to cart")
    public void iAddToCart(String product) {
        homePage.AddProduct(product);
    }

    @And("I checkout and add the shipping address and submit the details")
    public void iCheckoutAndAddTheShippingAddressAndSubmitTheDetails(DataTable dataTable) {
        System.out.println("Enter Address");
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String firstName = data.get(0).get("FirstName");
        String lastName = data.get(0).get("LastName");
        String address = data.get(0).get("Address");
        String state = data.get(0).get("State");
        String postalCode = data.get(0).get("PostalCode");
        homePage.CheckoutAndAddAddressDetails(firstName, lastName, address, state, postalCode);
    }

    @Then("I Verify Order placed successfully")
    public void iVerifyOrderPlacedSuccessfully() {
        homePage.VerifyOrderConfirmation();
    }
}
