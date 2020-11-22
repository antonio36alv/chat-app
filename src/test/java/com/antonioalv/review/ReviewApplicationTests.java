package com.antonioalv.review;

import com.antonioalv.review.model.ChatMessage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	@BeforeAll
	public static void beforeAll() {
		// WebDriverManager.chromedriver().setup();
		// driver = new ChromeDriver();

//		String chromeDriverPath = "C:\\Program Files (x86)\\chromedriver.exe";
		String chromeDriverPath = "/usr/bin/chromedriver";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		driver = new ChromeDriver(options);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

	@Test
	public void userSignLoginChatTest() {

		String firstName = "Buckley";
		String lastName = "Payne";

		String username = "Buckley";
		String password = "Angel";

		String messageText = "Smell the meat, not the heat";

		driver.get(baseURL + "/signup");

		SignupPage signup = new SignupPage(driver);
		signup.signup(firstName, lastName, username, password);

		driver.get(baseURL + "/login");

		LoginPage login = new LoginPage(driver);
		login.login(username, password);

		driver.get(baseURL + "/chat");

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendMessage(messageText, "Say");

		ChatMessage sentMessage = chatPage.getFirstMessage();

		assertEquals(username, sentMessage.getUsername());
		assertEquals(messageText, sentMessage.getMessageText());
	}

}
