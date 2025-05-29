package com.whliu.superaiagent.mapper;

import com.whliu.superaiagent.model.domain.MessageToolCall;
import com.whliu.superaiagent.model.domain.MessageToolResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageToolResponseMapper {

    void insertToolResponse(MessageToolResponse messageToolResponse);

    /**
     * 批量插入messageToolResponseList数据
     * @param messageToolResponseList
     */
    void batchInsertToolResponse(@Param("messageToolResponseList") List<MessageToolResponse> messageToolResponseList);

    /**
     * 单个查询
     * @param messageId
     * @return
     */
    List<MessageToolResponse> selectByMessageId(@Param("messageId") Long messageId);

    /**
     * 批量查询
     * @param messageIds
     * @return
     */
    List<MessageToolResponse> selectByMessageIds(@Param("messageIds") List<Long> messageIds);
}
