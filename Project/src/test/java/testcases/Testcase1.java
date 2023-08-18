package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
//import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.interactions.Actions;
import org.apache.poi.ss.usermodel.DateUtil;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.Assert;
import java.util.Set;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.LoggerHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

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
     

@DataProvider(name = "testData")
    public Object[][] readTestData() throws IOException {
        String excelFilePath = System.getProperty("user.dir") + "/src/test/java/resources/Testdata.xlsx";
        String sheetName = "Sheet1";
    
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
    
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
    
            Object[][] searchDataArray = new Object[rowCount][9]; 
    
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
    
                searchDataArray[i - 1][0] = getStringCellValue(row.getCell(0));
                searchDataArray[i - 1][1] = getStringCellValue(row.getCell(1));
                searchDataArray[i - 1][2] = getStringCellValue(row.getCell(2));
                searchDataArray[i - 1][3] = getStringCellValue(row.getCell(3));
                searchDataArray[i - 1][4] = getStringCellValue(row.getCell(4));
                searchDataArray[i - 1][5] = getStringCellValue(row.getCell(5));
                searchDataArray[i - 1][6] = getStringCellValue(row.getCell(6));
                searchDataArray[i - 1][7] = getStringCellValue(row.getCell(7));
                searchDataArray[i - 1][8] = getStringCellValue(row.getCell(8));
            }
    
            return searchDataArray;
        }
    }
    
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            default:
                return "";
        }
    }
    

    
    @Test(priority = 1, dataProvider = "testData")
    public void addLowCostGiftCard(String recipientName, String recipientEmail, String senderName, String senderEmail, String City, String Address, String postalcode, String Phonenumber, String Book ) throws InterruptedException, IOException {
        try {
            ExtentTest test = reporter.createTest("Add Low-Cost Gift Card", "Execution for adding a low-cost gift card");
            e = new EventHandler();
            driver.get(prop.getProperty("url") + "/");
            log.info("Browser launched");
            driver.manage().window().maximize();
            
            driver.findElement(By.partialLinkText("Gift")).click();
            Select sortBy = new Select(driver.findElement(By.id("products-orderby")));
            sortBy.selectByVisibleText("Price: High to Low");
            log.info("List sorted");
            List<WebElement> searchResult = driver.findElements(By.xpath("//input[@value='Add to cart']"));
            searchResult.get(searchResult.size() - 1).click();
            log.info("******************");
            driver.findElement(By.id("giftcard_1_RecipientName")).sendKeys(recipientName);
            driver.findElement(By.id("giftcard_1_RecipientEmail")).sendKeys(recipientEmail);
            driver.findElement(By.id("giftcard_1_SenderName")).sendKeys(senderName);
            driver.findElement(By.id("giftcard_1_SenderEmail")).sendKeys(senderEmail);
            driver.findElement(By.id("add-to-cart-button-1")).click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
            WebElement cartQtySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='cart-qty']")));
            Assert.assertTrue(cartQtySpan.getText().contains("1"));
            test.pass("Test passed successfully");

            
        } catch (Exception ex) {
        ExtentTest test = reporter.createTest("Add Low-Cost Gift Card");
        test.log(Status.FAIL, "Unable to find Razor Turbo",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "find-razor-turbo"))
                        .build());
        }
    }


    
    @Test(priority = 2, dataProvider = "testData")
    public void TestCheckButton(String recipientName, String recipientEmail, String senderName, String senderEmail, String City, String Address, String postalcode, String Phonenumber, String Book ) throws InterruptedException, IOException {
        try {
            ExtentTest test = reporter.createTest("TestCheckButton", "Execution for Checkout Button");
            e = new EventHandler();
            driver.get(prop.getProperty("url") + "/");
            log.info("Browser launched");
            driver.manage().window().maximize();
            log.info("******************");
            driver.findElement(By.partialLinkText("Gift")).click();
            Select sortBy = new Select(driver.findElement(By.id("products-orderby")));
            sortBy.selectByVisibleText("Price: High to Low");
            
            List<WebElement> searchResult = driver.findElements(By.xpath("//input[@value='Add to cart']"));
            searchResult.get(searchResult.size() - 1).click();
            
            driver.findElement(By.id("giftcard_1_RecipientName")).sendKeys(recipientName);
            driver.findElement(By.id("giftcard_1_RecipientEmail")).sendKeys(recipientEmail);
            driver.findElement(By.id("giftcard_1_SenderName")).sendKeys(senderName);
            driver.findElement(By.id("giftcard_1_SenderEmail")).sendKeys(senderEmail);
            driver.findElement(By.id("add-to-cart-button-1")).click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
            driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]")).click();
            Assert.assertFalse(driver.findElement(By.id("termsofservice")).isSelected());
            driver.findElement(By.xpath("//input[@id='termsofservice']")).click();
            driver.findElement(By.xpath("//button[@id='checkout']")).click();
            test.pass("Test passed successfully");

            
        } catch (Exception ex) {
       ExtentTest test = reporter.createTest("TestCheckButton");
        test.log(Status.FAIL, "Unable to find Razor Turbo",
        MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "find-razor-turbo"))
                .build());
        }
    }
     @Test(priority = 3, dataProvider = "testData")
    
     public void TestCheckout(String recipientName, String recipientEmail, String senderName, String senderEmail, String City, String Address, String postalcode, String Phonenumber, String Book ) throws InterruptedException, IOException {
            try {
                ExtentTest test = reporter.createTest("TestCheckButton", "Execution for Checkout Button");
                e = new EventHandler();
                driver.get(prop.getProperty("url") + "/");
                log.info("Browser launched");
                driver.manage().window().maximize();
                
                driver.findElement(By.partialLinkText("Gift")).click();
                Select sortBy = new Select(driver.findElement(By.id("products-orderby")));
                sortBy.selectByVisibleText("Price: High to Low");
                
                List<WebElement> searchResult = driver.findElements(By.xpath("//input[@value='Add to cart']"));
                searchResult.get(searchResult.size() - 1).click();
                
                driver.findElement(By.id("giftcard_1_RecipientName")).sendKeys(recipientName);
                driver.findElement(By.id("giftcard_1_RecipientEmail")).sendKeys(recipientEmail);
                driver.findElement(By.id("giftcard_1_SenderName")).sendKeys(senderName);
                driver.findElement(By.id("giftcard_1_SenderEmail")).sendKeys(senderEmail);
                driver.findElement(By.id("add-to-cart-button-1")).click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
                driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]")).click();
                Assert.assertFalse(driver.findElement(By.id("termsofservice")).isSelected());
                driver.findElement(By.xpath("//input[@id='termsofservice']")).click();
                driver.findElement(By.xpath("//button[@id='checkout']")).click();
                driver.findElement(By.xpath("//body/div[4]/div[1]/div[4]/div[2]/div[1]/div[2]/div[1]/div[1]/div[3]/input[1]")).click();
                driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys(recipientName);
                driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys(senderName);
                driver.findElement(By.id("BillingNewAddress_Email")).sendKeys(senderEmail);
                Select country = new Select(driver.findElement(By.xpath("//select[@id='BillingNewAddress_CountryId']")));
                country.selectByVisibleText("United States");
                driver.findElement(By.id("BillingNewAddress_City")).sendKeys(City);
                log.info("******************");
                driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys(Address);
                driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys(postalcode);
                driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys(Phonenumber);
                driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ol[1]/li[1]/div[2]/div[1]/input[1]")).click();
                driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ol[1]/li[2]/div[2]/div[1]/input[1]")).click();
                driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ol[1]/li[3]/div[2]/div[1]/input[1]")).click();
                driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ol[1]/li[4]/div[2]/div[2]/input[1]")).click();
                driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/div[1]/div[2]/input[1]")).click();
                test.pass("Test passed successfully");  

            } catch (Exception ex) {
               
       ExtentTest test = reporter.createTest("Test Checkout");
        test.log(Status.FAIL, "Unable to find Razor Turbo",
        MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "find-razor-turbo"))
                .build());
            }
        }
    @Test(priority = 4, dataProvider = "testData")
    
        public void Booksearch(String recipientName, String recipientEmail, String senderName, String senderEmail, String City, String Address, String postalcode, String Phonenumber, String Book ) throws InterruptedException, IOException {
               try {
                   ExtentTest test = reporter.createTest("TestCheckButton", "Execution for Checkout Button");
                   e = new EventHandler();
                   driver.get(prop.getProperty("url") + "/");
                   log.info("Browser launched");
                   driver.manage().window().maximize();
                   driver.findElement(By.id("small-searchterms")).sendKeys(Book);
                   driver.findElement(By.xpath("//input[@value='Search']")).click();
                   WebElement searchInput = driver.findElement(By.id("Q"));
                   String inputValue = searchInput.getAttribute("value");
                   Assert.assertTrue(inputValue.contains("book"), "Search input value contains 'book'");
                   test.pass("Test passed successfully", MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "find-razor-turbo"))
                   .build());  
   
               } catch (Exception ex) {
                  
          ExtentTest test = reporter.createTest("Test Checkout");
           test.log(Status.FAIL, "Unable to find Razor Turbo",
           MediaEntityBuilder.createScreenCaptureFromPath(screenshotHandler.captureScreenshot(driver, "find-razor-turbo"))
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