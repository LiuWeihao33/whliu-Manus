create table if not exists agent.chat_message
(
    id           bigint auto_increment comment '消息id' primary key,
    conversation_id  varchar(256)                   not null comment '会话id',
    message_type  ENUM('USER', 'ASSISTANT', 'SYSTEM', 'TOOL')   null comment '会话种类',
    text_content  text                   comment '用户输入内容',
    metadata  json                   comment 'Map<String,Object>的键值对，存储一些元信息',
    created_at  timestamp  default current_timestamp  comment '创建时间',
    index idx_conversation_id (conversation_id) comment '索引，用于根据id快速查询',
    index idx_created_at (created_at) comment '索引，用于根据时间快速检索'
)
    comment '主消息表';

create table if not exists agent.user_media
(
    id           bigint auto_increment comment 'id' primary key,
    message_id  bigint                   not null comment '消息id',
    mime_type  varchar(100)   null comment '标识文件格式和内容类型的标准化标识符，例如text/plain，image/jpeg这种',
    media_data  longblob                comment '存放media数据，二进制存储',
    media_url  varchar(500)            comment 'media的路径',
    foreign key (message_id) references chat_message(id) on delete cascade
)
    comment '媒体内容表（UserMessage的media属性）';

create table if not exists agent.message_tool_call
(
    id           bigint auto_increment comment 'id' primary key,
    message_id  bigint                   not null comment '消息id',
    tool_call_id  varchar(256)   null comment '工具调用id',
    tool_name  varchar(100)   null comment '工具名称',
    tool_type  varchar(100)   null comment '工具种类',
    arguments  json                   comment '工具参数，以json格式返回',
    foreign key (message_id) references chat_message(id) on delete cascade
)
    comment '工具调用表（AssistantMessage的toolCalls属性）';

create table if not exists agent.message_tool_response
(
    id           bigint auto_increment comment 'id' primary key,
    message_id  bigint                   not null comment '消息id',
    tool_response_id  varchar(256)   null comment '工具响应id',
    tool_name  varchar(100)   null comment '工具名称',
    response_data  text               comment '工具调用响应数据',
    foreign key (message_id) references chat_message(id) on delete cascade
)
    comment '工具响应表（ToolResponseMessage的responses属性）';


drop table message_tool_call;

drop table message_tool_response;

drop table user_media;

drop table chat_message;