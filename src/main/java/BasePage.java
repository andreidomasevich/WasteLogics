import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public abstract class BasePage {
    private final WebDriver driver;
    private static final int TIMEOUT = 5;

    BasePage(WebDriver driver){
        this.driver = driver;
    }

    public void click(WebElement element){
        element.click();
    }

    public WebElement find(By locator){
        return driver.findElement(locator);
    }

    public List<WebElement> findList(By locator){
        return driver.findElements(locator);
    }

    public void waitForPageLoad(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
