import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;

public class BaseTest {
    private WebDriver driver;

    BaseTest(){
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
    }
    public void open(String url){
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

}
