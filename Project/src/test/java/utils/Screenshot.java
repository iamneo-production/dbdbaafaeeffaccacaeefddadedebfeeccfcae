// package utils;

// import java.io.*;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.TimeZone;

// import org.openqa.selenium.OutputType;
// import org.openqa.selenium.TakesScreenshot;
// import org.openqa.selenium.WebDriver;

// public class Screenshot {
//     public String captureScreenshot(WebDriver driver, String screenshotName) {

//         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

//         TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata"); // IST timezone

//         dateFormat.setTimeZone(istTimeZone);

//         String timestamp = dateFormat.format(new Date());

//         // Define the screenshots directory path
//         String screenshotsDirectory = System.getProperty("user.dir") + "/src/main/screenshots/";

//         // Create the screenshots directory if it doesn't exist
//         File directory = new File(screenshotsDirectory);

//         if (!directory.exists()) {
//             directory.mkdirs();
//         }

//         File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//         String destinationScreenshotPath = screenshotsDirectory + screenshotName + "_" + timestamp + ".png";

//         try {
//             // Copy the screenshot file using Files.copy
//             java.nio.file.Files.copy(srcScreenshot.toPath(), new File(destinationScreenshotPath).toPath());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         return destinationScreenshotPath;
//     }
// }
package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Screenshot {
    public String captureScreenshot(WebDriver driver, String screenshotName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata"); // IST timezone
        dateFormat.setTimeZone(istTimeZone);
        String timestamp = dateFormat.format(new Date());

        // Define the screenshots directory path
        String screenshotsDirectory = "src/main/Screenshot/";
        String projectDirectory = System.getProperty("user.dir"); 

        // Create the screenshots directory if it doesn't exist
        File directory = new File(screenshotsDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationScreenshotPath = screenshotsDirectory + screenshotName + "_" + timestamp + ".png";

        try {
            // Copy the screenshot file using Files.copy
            Files.copy(srcScreenshot.toPath(), new File(destinationScreenshotPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destinationScreenshotPath;
    }
}
