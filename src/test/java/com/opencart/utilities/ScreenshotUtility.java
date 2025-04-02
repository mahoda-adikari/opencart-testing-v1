package com.opencart.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtility.class);
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;

    public static String captureScreen (WebDriver driver, String testName) {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (driver == null) {
                logger.error("Failed to capture screenshot: WebDriver instance is null");
                return "";
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS_z").format(new Date());
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String targetFilePath = SCREENSHOT_DIR + testName + "_" + timeStamp + ".png";

            FileUtils.copyFile(sourceFile, new File(targetFilePath));
            logger.info("Screenshot captured: " + targetFilePath);
            return targetFilePath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return "";
        }
    }
}
