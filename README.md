# BrowserStack-Cucumber-Parallel-Tests
Cucumber parallel tests 

# Set BrowserStack Credentials
Saving your BrowserStack credentials as environment variables makes it simple to run your test suite from your local or CI environment.

# To execute the Test in BrowserStack:
1. Navigate to the folder cd cucumber-java-parallel
2. Run the command `mvn install` it will download all the dev dependencies
3. To Run the Test in BrowserStack Automate Run the command `mvn clean test -DsuiteXmlFile=testng.xml`