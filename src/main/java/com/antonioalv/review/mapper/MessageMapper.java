package com.antonioalv.review.mapper;

import com.antonioalv.review.model.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

//    @Select("SELECT * FROM MESSAGES WHERE messageid = #{messageId}")
    @Select("SELECT * FROM MESSAGES")
    List<ChatMessage> getAllMessages();
//    ChatMessage getMessage(int messageId);

    @Insert("INSERT INTO MESSAGES(username, messagetext) VALUES(#{username}, #{messageText})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    int addMessage(ChatMessage chatMessage);
}
