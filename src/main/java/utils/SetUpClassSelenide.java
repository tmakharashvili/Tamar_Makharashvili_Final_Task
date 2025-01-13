package utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;

public class SetUpClassSelenide {
    @BeforeTest
    public static void setUp() {
        // ChromeOptions-ის კონფიგურაცია ინკოგნიტო რეჟიმისთვის
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        // WebDriver-ის კონფიგურაცია
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;

    }
}
