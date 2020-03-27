package com.rest.api.chat.service;

import com.rest.api.chat.model.ChatMessage;
import com.rest.api.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    /*
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return "";
        }
    }

    /*
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage message) {
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 방에 입장했습니다.");
            message.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 방에서 나갔습니다.");
            message.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
