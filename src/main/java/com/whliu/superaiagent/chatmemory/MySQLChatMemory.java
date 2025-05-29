
package com.whliu.superaiagent.chatmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

/**
 * 将会话历史记录保存到MySQL中
 */
public class MySQLChatMemory implements ChatMemory {

	Map<String, List<Message>> conversationHistory = new ConcurrentHashMap<>();

	@Override
	public void add(String conversationId, Message message) {
		this.add(conversationId, List.of(message));
	}

	@Override
	public void add(String conversationId, List<Message> messages) {
		this.conversationHistory.putIfAbsent(conversationId, new ArrayList<>());
		this.conversationHistory.get(conversationId).addAll(messages);
	}

	@Override
	public List<Message> get(String conversationId, int lastN) {
		List<Message> all = this.conversationHistory.get(conversationId);
		return all != null ? all.stream().skip(Math.max(0, all.size() - lastN)).toList() : List.of();
	}

	@Override
	public void clear(String conversationId) {
		this.conversationHistory.remove(conversationId);
	}

}
