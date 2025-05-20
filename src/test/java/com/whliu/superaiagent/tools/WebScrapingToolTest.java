package com.whliu.superaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapingToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapingTool tool = new WebScrapingTool();
        String url = "https://www.baidu.com";
        String result = tool.scrapeWebPage(url);
        Assertions.assertNotNull(result);
    }
}