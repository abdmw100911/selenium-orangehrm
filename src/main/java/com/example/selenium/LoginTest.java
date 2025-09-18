package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginTest {

    public static void main(String[] args) {
        // ✅ Set path to ChromeDriver (update if your folder changes)
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Step 1: Open OrangeHRM demo site
            driver.get("https://opensource-demo.orangehrmlive.com/");

            // Step 2: Try wrong logins
            testLogin(driver, "ABDULLAH MEWAWALA", "wrongPass1");
            testLogin(driver, "wrongUser2", "wrongPass2");

            // Step 3: Correct login
            testLogin(driver, "Admin", "admin123");

            // Pause so you can see final result
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void testLogin(WebDriver driver, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for username field to be visible
        WebElement userField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        WebElement passField = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        // Clear fields before typing
        userField.clear();
        passField.clear();

        // Enter credentials
        userField.sendKeys(username);
        passField.sendKeys(password);
        loginBtn.click();

        // Wait for either dashboard OR error message
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("dashboard"),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-alert-content-text"))
            ));
        } catch (Exception e) {
            // ignore timeout
        }

        // Print result
        if (driver.getCurrentUrl().contains("dashboard")) {
            System.out.println("✅ Login successful with: " + username + " / " + password);
        } else {
            try {
                WebElement errorMsg = driver.findElement(By.cssSelector(".oxd-alert-content-text"));
                System.out.println("❌ Login failed with: " + username + " / " + password +
                        " | Message: " + errorMsg.getText());
            } catch (Exception e) {
                System.out.println("❌ Login failed with: " + username + " / " + password);
            }
        }
    }
}
