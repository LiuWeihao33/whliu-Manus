package com.whliu.superaiagent.mapper;

import com.whliu.superaiagent.model.domain.MessageToolCall;
import com.whliu.superaiagent.model.domain.UserMedia;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageToolCallMapper {

    void insertToolCall(MessageToolCall messageToolCall);

    /**
     * 批量插入messageToolCallList数据
     * @param messageToolCallList
     */
    void batchInsertToolCall(@Param("messageToolCallList") List<MessageToolCall> messageToolCallList);

    /**
     * 单个查询
     * @param messageId
     * @return
     */
    List<MessageToolCall> selectByMessageId(@Param("messageId") Long messageId);

    /**
     * 批量查询
     * @param messageIds
     * @return
     */
    List<MessageToolCall> selectByMessageIds(@Param("messageIds") List<Long> messageIds);
}
