package io.cyclelabs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.List;

public class GrocerStepDefinitions {

    WebDriver driver = null;
    Config config = ConfigFactory.parseResources("testdata.conf").resolve();

    @Before
    public void setUp() {
        this.driver = startBrowser();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Grocer.io Web Application"); 
        }
        this.driver.quit();
    }
    
    @Given("I log into the Grocer IO site")
    public void i_log_into_the_site() {
        String url = config.getString("credentials.url");
        String userName = config.getString("credentials.username");
        String password = config.getString("credentials.password");
        driver.get(url);
        String userNameInput = config.getString("loginPage.userNameInput");
        String passwordInput = config.getString("loginPage.passwordInput");
        String loginButton = config.getString("loginPage.loginButton");
        By by = parseBy(loginButton);
        driver.findElement(parseBy(userNameInput)).sendKeys(userName);
        driver.findElement(parseBy(passwordInput)).sendKeys(password);
        driver.findElement(parseBy(loginButton)).click();
    }

    @Given("the {string} page is displayed")
    public void the_page_is_displayed(String title) {
        By by = By.xpath("//*[text()='" + title + "']");
        driver.findElement(by);
    }

    @When("I click the {word} button")
    public void i_click_the_button(String prop) {
        String locator = parseParamter(prop);
        By by = parseBy(locator);
        driver.findElement(by).click();
    }

    // @When("I enter {string} in the {word} field")
    // public void i_enter_in_the_field(String text, String prop) {
    //     String locator = parseParamter(prop);
    //     By by = parseBy(locator);
    //     driver.findElement(by).sendKeys(parseParamter(text));
    // }

    @When("I enter the following order:")
    public void i_enter_the_following_order(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (List<String> columns : rows) {
            String field = columns.get(0);
            String value = columns.get(1);
            // skip header row
            if (!field.equals("field")) {
                String locator = parseParamter(field);
                By by = parseBy(locator);
                driver.findElement(by).sendKeys(value);
            }
        }
    }

    @Then("I see {string} in the list")
    public void i_see_the_in_the_list(String string) {
        By by = By.xpath("//*[text()='" + string + "']");
        driver.findElement(by);   
    }

    private WebDriver startBrowser() {
        String path = config.getString("chromeDriverPath");
        System.setProperty("webdriver.chrome.driver", path); 
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(config.getDuration("implicitlyWait"));
        driver.manage().timeouts().scriptTimeout(config.getDuration("scriptTimeout"));
        driver.manage().timeouts().pageLoadTimeout(config.getDuration("pageLoadTimeout"));
        return driver;
    }

    private By parseBy(String input) {
        int index = input.indexOf(":");
        String locatorType = input.substring(0, index).toLowerCase();
        String locatorValue = input.substring(index + 1);
        if (locatorType.equals("name")) {
            return By.name(locatorValue);
        } else if (locatorType.equals("classname")) {
            return By.className(locatorValue);
        } else if (locatorType.equals("text")) {
            return By.xpath("//*[text()='" + locatorValue + "']");
        } else if (locatorType.equals("id")) {
            return By.id(locatorValue);
        } else if (locatorType.equals("xpath")) {
            return By.xpath(locatorValue);
        }
        return null;
    }

    private String parseParamter(String input) {
        if (input.startsWith("{") && input.endsWith("}")) {
            String lookup = input.substring(1, input.length() - 1);
            return config.getString(lookup);
        }
        return input;
    }

}
