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
                我的另一半居住在苏州市吴中区，请帮我找到 5 公里内合适的约会地点，
                并结合一些网络图片，制定一份详细的约会计划，
                并以 PDF 格式输出""";
        String answer = whliuManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }

}