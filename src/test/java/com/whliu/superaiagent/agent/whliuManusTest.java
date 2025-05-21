package com.whliu.superaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class whliuManusTest {

    @Resource
    private whliuManus whliuManus;

    @Test
    public void test() {
        String userPrompt = """
                我的现在在东太湖大道，请帮我找到 5 公里内的中餐馆，
                给出中餐馆的图片，并用中文给出推荐理由
                并以 PDF 格式输出""";
        String answer = whliuManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }

}