package com.whliu.superaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PgVectorVectorStoreConfigTest {

    @Resource
    private VectorStore PgVectorvectorStore;

    @Test
    void test() {
        List<Document> documents = List.of(
                new Document("我喜欢一个人", Map.of("meta1", "meta1")),
                new Document("我喜欢上了一个人"),
                new Document("我喜欢一个人呆着", Map.of("meta2", "meta2")));
        // 添加文档
        PgVectorvectorStore.add(documents);
        // 相似度查询
        List<Document> results = PgVectorvectorStore.similaritySearch(SearchRequest.builder().query("我有喜欢的女生吗").topK(3).build());
        Assertions.assertNotNull(results);
    }
}