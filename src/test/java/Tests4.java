import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Лешка on 21.12.2016.
 */
public class Tests4 {
    EventFiringWebDriver driver;
    WebDriverWait explWait;

    @BeforeClass
    public void preparation() {
        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", property);
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new EventHandler());
        explWait = new WebDriverWait(driver, 7);
        Reporter.setEscapeHtml(false);
    }

    @Test
    public void point1() {
            log("Open main page");
        driver.navigate().to("http://www.bing.com/");
            log("Check page title");
            log("Page title is: " + driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Bing", "Unexpected page title!");

    }
    @Test
    public void point2and3() {
        WebDriver.Options options = driver.manage();
        options.timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        options.window().maximize();
            log("Window was maximized");
        driver.findElement(By.cssSelector("#scpl1")).click();
        explWait.until(ExpectedConditions.titleIs("Лента изображений Bing"));
            log("Waiting for loading page title");

        JavascriptExecutor jse = driver;    //3
        for (int i=1; i<=3; i++) {
            List<WebElement> list = driver.findElements(By.xpath("//*[@class= 'iuscp varh']"));
            jse.executeScript("window.scrollBy(0,2000)");
            explWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[@class= 'iuscp varh']"), list.size()));

            List<WebElement> list2 = driver.findElements(By.xpath("//*[@class= 'iuscp varh']"));
            Assert.assertTrue(list2.size()>list.size(), "New images didn't load after scroll");
                log("Page was scrolled");
        }
    }

    @Test(dataProvider = "point4", dataProviderClass = DataProviders.class, dependsOnMethods = "point2and3")
    public void point4(String text) {
        log("Searching using different parameters");
        WebElement field = driver.findElement(By.cssSelector("#sb_form_q"));          //4
        JavascriptExecutor jse = driver;
        jse.executeScript("window.scrollTo(0,sb_form_q.offsetTop)");
        field.sendKeys(text);
        driver.findElement(By.id("sb_form_go")).click();
        explWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='dg_u']")));
        Assert.assertTrue(driver.findElements(By.xpath("//*[@class='dg_u']")).size()>0, "Images haven't loaded");
        log("Search was successful with the characters " + text);
        field = driver.findElement(By.cssSelector("#sb_form_q"));
        field.clear();
    }

    @Test (dependsOnMethods = "point4")         //5
    public void point5() {
        WebElement image1 = driver.findElement(By.xpath("(//div[@class='dg_u'])[1]"));
        Actions builder = new Actions(driver);
        builder.moveToElement(image1).build().perform();
        explWait.until(ExpectedConditions.presenceOfElementLocated(By.className("irhc")));
        log("Checking of presence of elements");
        Assert.assertNotNull(driver.findElement(By.className("irhc")), "enlarged image haven't displayed");
        Assert.assertNotNull(driver.findElement(By.xpath("//*[@title='Сохранить это изображение']")),
                "Button of save isn't embedded");
        Assert.assertNotNull(driver.findElement(By.xpath("//*[@title='Поиск по изображению']")),
                "Button of search by image isn't embedded");
        Assert.assertNotNull(driver.findElement(By.xpath("//*[@title='Пометить как изображение для взрослых']")),
                "Button of alarm isn't embedded");
        log("All elements appeared after hovering over the image");

    }

    @Test (dependsOnMethods = "point5")
    public void point6() {
        WebElement searchByImage = driver.findElement(By.xpath("//*[@title='Поиск по изображению']"));
        searchByImage.click();
        explWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='detail_canvas']")));
        Assert.assertNotNull(driver.findElement(By.className("closebutton")), "Closebutton isn't embedded");
        log("Closebutton is present at the page of slide show");
    }

    @Test (dependsOnMethods = "point6")
    @Parameters("minNumberOfImages")
    public void point7(Integer minNumberOfImages) {
        driver.findElement(By.xpath("//*[text()='Смотреть другие изображения']")).click();
        Assert.assertTrue(driver.findElements(By.xpath("//*[@class='iuscp varh']")).size()>minNumberOfImages,
                "There is no additional related images");
        log("Images were loaded");
    }



    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    public static void log(String message) {
        Reporter.log(message + "<br>");
    }

}

