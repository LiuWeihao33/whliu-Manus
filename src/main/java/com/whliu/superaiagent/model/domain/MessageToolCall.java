package com.whliu.superaiagent.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具调用表（AssistantMessage的toolCalls属性）
 * @TableName message_tool_calls
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageToolCall {
    /**
     * id
     */
    private Long id;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 工具调用id
     */
    private String toolCallId;

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * 工具种类
     */
    private String toolType;

    /**
     * 工具参数，以json格式返回
     */
    private String arguments;
}