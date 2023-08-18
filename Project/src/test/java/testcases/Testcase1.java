package testcases;

import java.io.IOException;
import java.net.MalformedURLException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.Test;
import utils.LoggerHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import utils.LoggerHandler;
import utils.Screenshot;

import utils.EventHandler;
import utils.Reporter;
// import utils.Screenshot;
public class Testcase1 extends Base {
    EventHandler e;
    ExtentSparkReporter sparkReporter;
    ExtentReports reporter = Reporter.generateExtentReport();
    java.util.logging.Logger log =  LoggerHandler.getLogger();
    Screenshot screenshotHandler = new Screenshot();
    ChromeOptions options = new ChromeOptions();
     

    
    @Test(priority = 1)
    public void TestTodaysDeal() throws InterruptedException, IOException {
        try {
            ExtentTest test = reporter.createTest("TestTodaysDeals", "Execution for TestTodaysDea");
            e = new EventHandler();
            driver.get(prop.getProperty("url") + "/");
            options.addArguments("--remote-allow-origins=*");
            log.info("Browser launched");
            driver.manage().window().maximize();
            String CartValue=driver.findElement(By.id("nav-cart-count")).getText();
            log.info(CartValue);
            driver.findElement(By.linkText("Today's Deals")).click();
            String actualRes=driver.findElement(By.xpath("//h1")).getText();
            Assert.assertEquals(actualRes, "Today's Deals");
            test.pass("Test passed successfully");
 
           
            
        } catch (Exception ex) {
        ExtentTest test = reporter.createTest("Today's Deals");
        test.log(Status.FAIL, "Unable to Today's Deals",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "Today's Deals"))
                        .build());
        }
    }

    @Test(priority = 2)
    public void TestProductClick() throws InterruptedException, IOException {
        try {
            ExtentTest test = reporter.createTest("Product Click", "Execution for Product Click");
            e = new EventHandler();
            driver.get(prop.getProperty("url") + "/");
            options.addArguments("--remote-allow-origins=*");
            log.info("Browser launched");
            driver.manage().window().maximize();
            String CartValue=driver.findElement(By.id("nav-cart-count")).getText();
            driver.findElement(By.linkText("Today's Deals")).click();
            String actualRes=driver.findElement(By.xpath("//h1")).getText();
            Assert.assertEquals(actualRes, "Today's Deals");
            List<WebElement>  searchResult= driver.findElements(By.xpath("//div/img"));
            Thread.sleep(500);
            List<WebElement>  topic= driver.findElements(By.xpath("//div[@class='DealContent-module__truncate_sWbxETx42ZPStTc9jwySW']"));
            String productName=topic.get(1).getText();
            searchResult.get(2).click();
            String ActualproductName=driver.findElement(By.xpath("//h1")).getText();
            Assert.assertEquals(ActualproductName, productName);
            test.pass("Test passed successfully");

            
        } catch (Exception ex) {
        ExtentTest test = reporter.createTest("Click the second Product in today's deals");
        test.log(Status.FAIL, "Unable Click the second Product in today's deals",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "Click the second Product in today's deals"))
                        .build());
        }
    }

    @Test(priority = 3)
    public void AddCartTest() throws InterruptedException, IOException {
        try {
            ExtentTest test = reporter.createTest("Add Cart Test", "Execution for Add to Cart Test");
            e = new EventHandler();
            driver.get(prop.getProperty("url") + "/");
            options.addArguments("--remote-allow-origins=*");
            log.info("Browser launched");
            driver.manage().window().maximize();
            String CartValue=driver.findElement(By.id("nav-cart-count")).getText();
            log.info("CartValue" +CartValue);
            driver.findElement(By.linkText("Today's Deals")).click();
            String actualRes=driver.findElement(By.xpath("//h1")).getText();
            Assert.assertEquals(actualRes, "Today's Deals");
            List<WebElement>  searchResult= driver.findElements(By.xpath("//div/img"));
            Thread.sleep(500);
            List<WebElement>  topic= driver.findElements(By.xpath("//div[@class='DealContent-module__truncate_sWbxETx42ZPStTc9jwySW']"));
            String productName=topic.get(1).getText();
            searchResult.get(2).click();
            String ActualproductName=driver.findElement(By.xpath("//h1")).getText();
            log.info(productName +"     "  +ActualproductName);
            Assert.assertEquals(ActualproductName, productName);
            topic= driver.findElements(By.xpath("//div/div[@class='a-section octopus-dlp-asin-title']/a"));
            topic.get(1).click();
            driver.findElement(By.id("add-to-cart-button")).click();
            driver.findElement(By.xpath("//span[@id='attach-sidesheet-view-cart-button']/span[@class='a-button-inner']/input[@class='a-button-input']")).click();
            CartValue=driver.findElement(By.id("nav-cart-count")).getText();
            Assert.assertEquals(CartValue, "1");
            test.pass("Test passed successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "Unable to Add to Cart "))
            .build());

        } catch (Exception ex) {
        ExtentTest test = reporter.createTest("Add to Cart");
        test.log(Status.FAIL, "Unable to Add to Cart ",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "Unable to Add to Cart "))
                        .build());
        }
    }
       
@BeforeMethod
public void beforeMethod() throws MalformedURLException {
    openBrowser();

}

    @AfterMethod
    public void afterMethod() {
        driver.quit();
        reporter.flush();
        log.info("Browser closed");
        LoggerHandler.closeHandler();
    }
}