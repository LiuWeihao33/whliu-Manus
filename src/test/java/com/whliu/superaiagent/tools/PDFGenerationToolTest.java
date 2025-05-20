package com.whliu.superaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "测试pdf.pdf";
        String content = "测试文本 english";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}