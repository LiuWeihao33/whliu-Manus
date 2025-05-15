package com.whliu.superaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring AI框架调用AI大模型
 */

/**
 * CommandLineRunner实现单次执行的方法
 * 在spring项目启动的时候会检查Bean，如果注入的Bean中有实现了CommandLineRunner的run方法的
 * 会先进行调用
 */
@Component
public class SpringAiAiInvoke implements CommandLineRunner {


    @Resource
    private ChatModel dashscopeChatModel; // 这是一个接口，要指定是使用的dashscopeChatModel

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = dashscopeChatModel.call(new Prompt("你好，你是谁"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
