@Candidate
  Feature: BStackDemo.

    Scenario: Search Product in Amazon
      Given I navigate to "amazon"
      When I search for "iphone X"
      And I select Cell Phone Operating System as "iOS"
      And I sort the Price from High to Low
     Then I capture all the results on the First Page

    Scenario: Search Product in BStackDemo
      Given I navigate to bstackdemo
      And I SignIn with valid credentials
      And I add "iPhone 12" to cart
      When I checkout and add the shipping address and submit the details
        | FirstName | LastName | Address  | State     | PostalCode |
        | Demo      | User     | H.no 123 | Telangana | 500019     |
      Then I Verify Order placed successfully
