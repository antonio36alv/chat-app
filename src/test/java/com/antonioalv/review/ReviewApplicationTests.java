package com.antonioalv.review;

import com.antonioalv.review.model.ChatMessage;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.net.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ReviewApplicationTests {

	@LocalServerPort
	public int port;

    @Container
	public BrowserWebDriverContainer chrome =
			(BrowserWebDriverContainer) new BrowserWebDriverContainer()
					.withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
							new File("./target/"))
					.withCapabilities(new FirefoxOptions()
							.addArguments("--headless",
									"--disable-gpu",
									"--window-size=1920,1200",
									"--ignore-certificate-errors"))
				.withNetwork(Network.SHARED);

	public String baseURL;

//	@BeforeAll
//	public static void beforeAll() {
//		driver = chrome.getWebDriver();
		/*
		previously used
		// WebDriverManager.chromedriver().setup();
		// driver = new ChromeDriver();
		// for windows
		// String firefoxDriverPath = "C:\\Program Files (x86)\\firefoxdriver.exe";
		// for linux
		// String chromeDriverPath = "/usr/bin/chromedriver";
		// System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		// driver = new ChromeDriver(options);
		*/
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
//        chrome = new BrowserWebDriverContainer<>().withCapabilities(options);
//		chrome = new BrowserWebDriverContainer<>().withCapabilities(new ChromeOptions().addArguments());
//		driver = chrome.getWebDriver();
//		String firefoxDriverPath = "/usr/local/bin/geckodriver";
//		System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
//	}

//	@AfterAll
//	public static void afterAll() {
//		driver.quit();
//		driver = null;
//	}

	@BeforeEach
	public void beforeEach() {
//		baseURL = baseURL = "http://localhost:" + port;
		String hostIpAddress = chrome.getHost();
		System.out.println("here");
		System.out.println("here");
		System.out.println("here");
		System.out.println("here");
		System.out.println("here");
		System.out.println("here");
		System.out.println(hostIpAddress);
//		baseURL = "http://" + hostIpAddress + ":" + port;
//		baseURL = "http://" + hostIpAddress + ":" + port;
		org.testcontainers.Testcontainers.exposeHostPorts(port);
		chrome.addExposedPort(port);
		System.out.println(chrome.getExposedPorts());
	}

	@Test
	public void userSignLoginChatTest() {
		RemoteWebDriver driver = chrome.getWebDriver();

		String firstName = "Buckley";
		String lastName = "Payne";

		String username = "Buckley";
		String password = "Angel";

		String messageText = "Smell the meat, not the heat";

		System.out.println("i want pizza");
		System.out.println(chrome.getHost());

//		String hostIpAddress = "192.168.1.155";
		// https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
		String ip = "";
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
			System.out.println(String.format("my ip: %s", ip));
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		baseURL = "http://" + ip + ":" + port;
		System.out.println(baseURL);
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
