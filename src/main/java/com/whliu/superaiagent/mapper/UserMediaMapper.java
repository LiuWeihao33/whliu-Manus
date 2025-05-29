package com.whliu.superaiagent.mapper;

import com.whliu.superaiagent.model.domain.UserMedia;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMediaMapper {

    void insertMedia(UserMedia userMedia);

    /**
     * 批量插入media数据
     * @param mediaList
     */
    void batchInsertMedia(@Param("mediaList") List<UserMedia> mediaList);

    /**
     * 单个查询
     * @param messageId
     * @return
     */
    List<UserMedia> selectByMessageId(@Param("messageId") Long messageId);

    /**
     * 批量查询
     * @param messageIds
     * @return
     */
    List<UserMedia> selectByMessageIds(@Param("messageIds") List<Long> messageIds);
}
