package com.whliu.superaiagent.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 主消息表
 * @TableName chat_messages
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    /**
     * id
     */
    private Long id;

    /**
     * 会话id
     */
    private String conversationId;

    /**
     * 会话种类
     */
    private String messageType;

    /**
     * 用户输入内容
     */
    private String textContent;

    /**
     * Map<String,Object>的键值对，存储一些元信息
     */
    private String metadata;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}