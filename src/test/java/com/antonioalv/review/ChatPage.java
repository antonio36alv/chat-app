package com.antonioalv.review;

import com.antonioalv.review.model.ChatMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ChatPage {

    // Elements for messageText
    @FindBy(id = "messageText")
    private WebElement messageText;

    // Element for drop-down
    @FindBy(id = "messageType")
    private WebElement messageDrop;
    Select messageType;

    // Element for submit
    @FindBy(id = "submit")
    private WebElement submit;

    // Element for message text box
    @FindBy(className = "chatMessageUsername")
    private WebElement chatMessageUsername;

    @FindBy(className = "chatMessageText")
    private WebElement chatMessageText;

    public ChatPage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
        messageType = new Select(messageDrop);

    }

    // send keystrokes to messageText
    public void sendMessage(String message, String messageType) {
        this.messageText.clear();
        this.messageText.sendKeys(message);

        this.messageType.selectByValue(messageType);
        this.submit.click();
    }

    public ChatMessage getFirstMessage() {
        ChatMessage result = new ChatMessage();
        result.setMessageText(chatMessageText.getText());
        result.setUsername(chatMessageUsername.getText());
        return result;
    }
}
