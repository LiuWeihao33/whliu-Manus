package com.whliu.superaiagent.chatmemory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.ObjectMapper;
import com.whliu.superaiagent.model.domain.ChatMessage;
import com.whliu.superaiagent.model.domain.MessageToolCall;
import com.whliu.superaiagent.model.domain.MessageToolResponse;
import com.whliu.superaiagent.model.domain.UserMedia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于处理Message不同子类
 */
@Slf4j
public class MessageTool {
    /**
     * 将Spring AI的Message对象转换为数据库实体对象ChatMessage
     * @param conversationId
     * @param message
     * @return
     */
    public static ChatMessage createChatMessage(String conversationId, Message message) {
        ChatMessage chatMessage = new ChatMessage();
        // 处理可以赋值的基本属性
        chatMessage.setConversationId(conversationId);
        chatMessage.setMessageType(message.getMessageType().name()); // MessageType是枚举类，.name直接返回枚举申明的名称
        chatMessage.setTextContent(message.getText());

        // 处理meta_data
        Map<String, Object> metadata = message.getMetadata();
        if (metadata != null && !metadata.isEmpty()) {
            try {
                // 使用hutool工具类将json转换成String
                String metaJson = JSONUtil.toJsonStr(metadata);
                chatMessage.setMetadata(metaJson);
            } catch (Exception e) {
                log.info("json转成String序列化错误：" + e.getMessage());
                chatMessage.setMetadata("{}");
            }
        } else {
            chatMessage.setMetadata("{}");
        }
        return chatMessage;
    }

//    public static Message reconstructMessage(ChatMessage chatMessage,
//                                     Map<Long, List<UserMedia>> mediaMap,
//                                     Map<Long, List<MessageToolCall>> toolCallMap,
//                                     Map<Long, List<MessageToolResponse>> toolResponseMap){
//
//        // 解析metadata
//        Map<String, Object> metadata = parseMetadata(chatMessage.getMetadata());
//
//        switch (chatMessage.getMessageType().toUpperCase()) {
//            case "USER":
//                return reconstructUserMessage(chatMessage, mediaMap.get(chatMessage.getId()), metadata);
//            case "ASSISTANT":
//                return reconstructAssistantMessage(chatMessage, toolCallMap.get(chatMessage.getId()), metadata);
//            case "TOOL":
//                return reconstructToolResponseMessage(chatMessage, toolResponseMap.get(chatMessage.getId()), metadata);
//            case "SYSTEM":
//                return reconstructSystemMessage(chatMessage, metadata);
//            default:
//                throw new IllegalArgumentException("Unknown message type: " + chatMessage.getMessageType());
//        }
//    }
//
//    private UserMessage reconstructUserMessage(ChatMessage chatMessage, List<UserMedia> mediaList, Map<String, Object> metadata) {
//        String content = chatMessage.getTextContent();
//
//        if (mediaList == null || mediaList.isEmpty()) {
//            // 纯文本消息
//            return new UserMessage(content, metadata);
//        } else {
//            // 包含媒体内容的消息
//            List<Media> springMediaList = mediaList.stream()
//                    .map(this::convertToSpringMedia)
//                    .collect(Collectors.toList());
//
//            return new UserMessage(content, springMediaList, metadata);
//        }
//    }

    /**
     * 解析JSON格式的metadata
     */
    private static Map<String, Object> parseMetadata(String metadataJson) {
        // 使用Hutool的StrUtil判断字符串是否为空
        if (StrUtil.isBlank(metadataJson)) {
            return new HashMap<>();
        }

        try {
            // 使用Hutool的JSONUtil解析JSON字符串为Map
            return JSONUtil.parseObj(metadataJson);
        } catch (Exception e) {
            log.warn("Failed to parse metadata: {}", metadataJson, e);
            return new HashMap<>();
        }
    }
}
