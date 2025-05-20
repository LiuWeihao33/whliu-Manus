package com.whliu.superaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool tool = new TerminalOperationTool();
        String command = "dir"; // windows下命令执行会有问题
        String result = tool.executeTerminalCommand(command);
        Assertions.assertNotNull(result);
    }
}