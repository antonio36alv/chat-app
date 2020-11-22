package com.antonioalv.review.service;

import com.antonioalv.review.mapper.MessageMapper;
import com.antonioalv.review.model.ChatForm;
import com.antonioalv.review.model.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void addMessage(ChatForm chatForm) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setUsername(chatForm.getUsername());

        switch (chatForm.getMessageType()) {
		case "Say": 
			newMessage.setMessageText(chatForm.getMessageText());
			break;
		case "Shout":
			newMessage.setMessageText(chatForm.getMessageText().toUpperCase());
			break;
		default: 
			newMessage.setMessageText(chatForm.getMessageText().toLowerCase());
			break;
        }
        messageMapper.addMessage(newMessage);
    }

    public List<ChatMessage> getMessages() {
        return new ArrayList<>(messageMapper.getAllMessages());
    }
}
