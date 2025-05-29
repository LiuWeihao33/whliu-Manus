package com.whliu.superaiagent.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 媒体内容表（UserMessage的media属性）
 * @TableName user_media
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedia {
    /**
     * id
     */
    private Long id;

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 标识文件格式和内容类型的标准化标识符，例如text/plain，image/jpeg这种
     */
    private String mimeType;

    /**
     * media的路径
     */
    private String mediaUrl;

    /**
     * 存放media数据，二进制存储
     */
    private byte[] mediaData;
}