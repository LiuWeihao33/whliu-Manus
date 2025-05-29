
package com.whliu.superaiagent.chatmemory;
import java.time.LocalDateTime;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cn.hutool.json.JSONUtil;
import cn.hutool.json.ObjectMapper;
import com.whliu.superaiagent.mapper.ChatMessageMapper;
import com.whliu.superaiagent.mapper.MessageToolCallMapper;
import com.whliu.superaiagent.mapper.MessageToolResponseMapper;
import com.whliu.superaiagent.mapper.UserMediaMapper;
import com.whliu.superaiagent.model.domain.ChatMessage;
import com.whliu.superaiagent.model.domain.MessageToolCall;
import com.whliu.superaiagent.model.domain.MessageToolResponse;
import com.whliu.superaiagent.model.domain.UserMedia;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 将会话历史记录保存到MySQL中
 */
@Component
@Slf4j
public class MySQLChatMemory implements ChatMemory {

	@Autowired
	private ChatMessageMapper chatMessageMapper;

	@Autowired
	private MessageToolCallMapper messageToolCallMapper;

	@Autowired
	private MessageToolResponseMapper messageToolResponseMapper;

	@Autowired
	private UserMediaMapper userMediaMapper;

	@Override
	@Transactional
	public void add(String conversationId, Message message) {
		// 1. 插入主消息
		ChatMessage chatMessage = MessageTool.createChatMessage(conversationId, message);
		chatMessageMapper.insertMessage(chatMessage);
		// 2.处理特殊消息，继承Message的各种子类
		this.handleMessageSpecificData(conversationId, message);
	}

	@Override
	@Transactional
	public void add(String conversationId, List<Message> messages) {
		for (Message message : messages) {
			this.add(conversationId, message);
		}
	}

	@Override
	public List<Message> get(String conversationId, int lastN) {
		// 1. 获取基本消息信息chatMessage
		List<ChatMessage> chatMessages = chatMessageMapper.selectMessagesByConversationId(conversationId, lastN);
		if (chatMessages == null || chatMessages.isEmpty()) {
			return null;
		}
		// 2. 批量获取关联数据
		List<Long> messageIds = chatMessages.stream().map(ChatMessage::getId).toList();
		for (Long messageId : messageIds) {
			// userMedia
			List<UserMedia> userMediaList = userMediaMapper.selectByMessageId(messageId);
			Map<Long, List<UserMedia>> userMediaMap = new ConcurrentHashMap<>();
			userMediaMap.put(messageId, userMediaList);
			// messageToolCall
			List<MessageToolCall> messageToolCallList = messageToolCallMapper.selectByMessageId(messageId);
			Map<Long, List<MessageToolCall>> messageToolCallMap = new ConcurrentHashMap<>();
			messageToolCallMap.put(messageId, messageToolCallList);
			// messageToolResponse
			List<MessageToolResponse> messageToolResponseList = messageToolResponseMapper.selectByMessageId(messageId);
			Map<Long, List<MessageToolResponse>> messageToolResponseMap = new ConcurrentHashMap<>();
			messageToolResponseMap.put(messageId, messageToolResponseList);
		}
		// 3. 重建Message对象
//		List<Message> messages = chatMessages.stream()
//				.map(chatMessage -> reconstructMessage(chatMessage, mediaMap, toolCallMap, toolResponseMap))
//				.collect(Collectors.toList());
//
//		// 4. 恢复原始顺序
//		Collections.reverse(messages);
		return null;
	}

	@Override
	@Transactional
	public void clear(String conversationId) {
		chatMessageMapper.deleteByConversationId(conversationId);
	}

	/**
	 * 处理不同message的特殊属性
	 * @param messageId
	 * @param message
	 */
	private void handleMessageSpecificData(String messageId, Message message) {
		switch (message.getMessageType()) {
			case USER:
				handleUserMediaData(messageId, (UserMedia) message);
				break;
			case ASSISTANT:
				handleMessageToolCallData(messageId, (MessageToolCall) message);
				break;
			case TOOL:
				handleMessageToolResponseData(messageId, (MessageToolResponse) message);
				break;
			case SYSTEM:
				break;
			default:
				log.info("未知数据类型: " + message.getMessageType());
		}
	}

	private void handleUserMediaData(String messageId, UserMedia userMedia) {
		userMediaMapper.insertMedia(userMedia);
	}

	private void handleMessageToolCallData(String messageId, MessageToolCall messageToolCall) {
		messageToolCallMapper.insertToolCall(messageToolCall);
	}

	private void handleMessageToolResponseData(String messageId, MessageToolResponse messageToolResponse) {
		messageToolResponseMapper.insertToolResponse(messageToolResponse);
	}
}

