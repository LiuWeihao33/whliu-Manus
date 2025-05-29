package com.whliu.superaiagent.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具响应表（ToolResponseMessage的responses属性）
 * @TableName message_tool_responses
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageToolResponse {
    /**
     * id
     */
    private Long id;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 工具响应id
     */
    private String toolResponseId;

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * 工具调用响应数据
     */
    private String responseData;
}