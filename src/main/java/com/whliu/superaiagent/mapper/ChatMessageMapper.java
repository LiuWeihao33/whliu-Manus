package com.whliu.superaiagent.mapper;

import com.whliu.superaiagent.model.domain.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    /**
     * 添加会话
     * @param chatMessage
     */
    void insertMessage(ChatMessage chatMessage);
    /**
     * 查询会话
     * // @Param("conversationId")注解用于：
     *     // 明确参数名：为方法参数指定一个名称，MyBatis在映射SQL时会使用这个名称
     *     // 解决多参数问题：当方法有多个参数时，确保参数能正确绑定到SQL中
     * @param conversationId
     * @param limit
     * @return
     */
    List<ChatMessage> selectMessagesByConversationId(@Param("conversationId") String conversationId,
                                                     @Param("limit") int limit);

    /**
     * 删除会话
     * @param conversationId
     */
    void deleteByConversationId(@Param("conversationId") String conversationId);
}
