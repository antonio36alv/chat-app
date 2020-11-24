package com.antonioalv.review;

import com.antonioalv.review.model.ChatMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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
	public static BrowserWebDriverContainer firefox =
			(BrowserWebDriverContainer) new BrowserWebDriverContainer()
					.withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
										new File("./target/"))
					.withCapabilities(new FirefoxOptions().addArguments("--headless",
																		"--disable-gpu",
																		"--window-size=1920,1200",
																		"--ignore-certificate-errors"))
					.withNetwork(Network.SHARED);

	public static RemoteWebDriver driver;

	public String baseURL;
	public static String ip = "";

	@BeforeAll
	public static void beforeAll() {
		// init our remote web driver
		driver = firefox.getWebDriver();
		// in order to find host ip address where our app server is running
		// https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = String.format("http://%s:%d", ip, port);
		org.testcontainers.Testcontainers.exposeHostPorts(port);
		firefox.addExposedPort(port);
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
