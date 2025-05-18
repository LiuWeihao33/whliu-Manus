package com.whliu.superaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    private LoveAppDocumentLoader lovaAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        lovaAppDocumentLoader.loadMarkdowns();
    }
}