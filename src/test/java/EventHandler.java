import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventHandler implements WebDriverEventListener{

    public void beforeNavigateTo(String url, WebDriver driver) {
        Tests4.log("Moving to " + url.toUpperCase());
    }

    public void afterNavigateTo(String url, WebDriver driver) {

    }

    public void beforeNavigateBack(WebDriver driver) {

    }

    public void afterNavigateBack(WebDriver driver) {

    }

    public void beforeNavigateForward(WebDriver driver) {

    }

    public void afterNavigateForward(WebDriver driver) {

    }

    public void beforeNavigateRefresh(WebDriver driver) {

    }

    public void afterNavigateRefresh(WebDriver driver) {

    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        Tests4.log("Поиск элемента " + by);
    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        Tests4.log("Элемент найден: " + by);
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        Tests4.log("Clicking on element " + element.getText());
    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        Tests4.log("Click performed on " + element);
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        Tests4.log("Changing the value of " + element + " to " + keysToSend);
    }

    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        Tests4.log("The value of " + element + " have changed to " + keysToSend);
    }

    public void beforeScript(String script, WebDriver driver) {
        Tests4.log("Script " + script + " is executing");
    }

    public void afterScript(String script, WebDriver driver) {
        Tests4.log(script + " was executed");
    }

    public void onException(Throwable throwable, WebDriver driver) {
        Tests4.log(throwable.toString() + "was thrown");
    }
}
