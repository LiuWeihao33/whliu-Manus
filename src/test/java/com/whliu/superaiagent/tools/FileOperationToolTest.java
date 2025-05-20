package com.whliu.superaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "test.txt";
        String result = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(result);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "test.txt";
        String content = "content，内容";
        String result = fileOperationTool.writeFile(fileName, content);
        Assertions.assertNotNull(result);
    }
}