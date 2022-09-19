package io.cyclelabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App 
{

    public final static By OrderNumberLocator = parseBy("name:order_number");
    public final static By CustomerInputLocator = parseBy("name:order_customer");
    public final static By PriceLocator = parseBy("name:order_price");
    public final static By addButton = parseBy("xPath://button[text()='Add']");

    public static void main( String[] args )
    {
        Config config = ConfigFactory.parseResources("testdata.conf").resolve();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(9001, "Bruce Wayne", new BigDecimal("350")));
        orders.add(new Order(9002, "Jonathan Crane", new BigDecimal("19.99")));
        orders.add(new Order(9003, "Slade Wilson", new BigDecimal("200.50")));
        WebDriver driver = startDriver(config);
        try {
            login(config, driver);
            for (Order order : orders) {
                enterOrder(config, driver, order);
            }
        } finally {
            try {
                Thread.sleep(30000);
            } catch (Exception e) {}
            driver.quit();
            System.exit(0);
        }
    }

    private static WebDriver startDriver(Config config) {
        String path = config.getString("chromeDriverPath");
        System.setProperty("webdriver.chrome.driver", path); 
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(config.getDuration("implicitlyWait"));
        driver.manage().timeouts().scriptTimeout(config.getDuration("scriptTimeout"));
        driver.manage().timeouts().pageLoadTimeout(config.getDuration("pageLoadTimeout"));
        return driver;
    }

    private static void login(Config config, WebDriver driver) {
        String url = config.getString("credentials.url");
        String userName = config.getString("credentials.username");
        String password = config.getString("credentials.password");
        driver.get(url);
        String userNameInput = config.getString("loginPage.userNameInput");
        String passwordInput = config.getString("loginPage.passwordInput");
        String loginButton = config.getString("loginPage.loginButton");
        driver.findElement(parseBy(userNameInput)).sendKeys(userName);
        driver.findElement(parseBy(passwordInput)).sendKeys(password);
        driver.findElement(parseBy(loginButton)).click();
    }

    private static void enterOrder(Config config, WebDriver driver, Order order) {
        System.out.println("ADD IT!!!");

        String addOrderlocator = parseParamter(config, "{ordersPage.addOrderButton}");
        driver.findElement(parseBy(addOrderlocator)).click();
        driver.findElement(CustomerInputLocator).sendKeys(order.getCustomerName());
        driver.findElement(OrderNumberLocator).sendKeys(Integer.toString(order.getOrderNumber()));
        driver.findElement(PriceLocator).sendKeys(order.getPriceAsDollars());

        System.out.println("CLICK IT!!!");
        driver.findElement(addButton).click();
    }

    private static By parseBy(String input) {
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

    private static String parseParamter(Config config, String input) {
        if (input.startsWith("{") && input.endsWith("}")) {
            String lookup = input.substring(1, input.length() - 1);
            return config.getString(lookup);
        }
        return input;
    }
}
