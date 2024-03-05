package com.lambdatest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JUnitTodo {
    String username = System.getenv("LT_USERNAME") == null ? "your_LT_Username" : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "your_LT_AccessKey" : System.getenv("LT_ACCESS_KEY");

    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    public String status = "failed";

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "latest");
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build", "JUnitExtendedTestApp");
        ltOptions.put("name", "JUnitExtendedTest");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "junit-junit");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), browserOptions);
    }

    @Test
    public void testItemAddition() throws Exception {
        driver.get("https://lambdatest.github.io/sample-todo-app/");
        driver.findElement(By.id("sampletodotext")).sendKeys("Complete Selenium Test");
        driver.findElement(By.id("addbutton")).click();
        WebElement addedItem = driver.findElement(By.xpath("//span[contains(text(), 'Complete Selenium Test')]"));
        Assert.assertTrue(addedItem.isDisplayed());
        status = "passed";
    }

    @Test
    public void testItemCheck() throws Exception {
        driver.get("https://lambdatest.github.io/sample-todo-app/");
        driver.findElement(By.name("li3")).click();
        boolean isChecked = driver.findElement(By.name("li3")).isSelected();
        Assert.assertTrue(isChecked);
        status = "passed";
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
