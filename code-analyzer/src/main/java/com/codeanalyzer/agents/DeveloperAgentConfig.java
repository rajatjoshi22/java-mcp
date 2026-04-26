package com.codeanalyzer.agents;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.modelcontextprotocol.client.McpSyncClient;

@Configuration
public class DeveloperAgentConfig {


@Bean
public ChatClient developerAgent(ChatClient.Builder builder, List<McpSyncClient> mcpClients) {
      // Correct way: use McpToolUtils to get ToolCallbacks from sync clients
        List<org.springframework.ai.tool.ToolCallback> toolCallbacks = 
                McpToolUtils.getToolCallbacksFromSyncClients(mcpClients);
    return builder
        .defaultSystem("""
            You are a Lead Software Engineer. 
            Your goal is to implement production-ready code based on technical designs.
            1. Use the provided technical design to generate full source code.
            2. Ensure the code follows best practices for the requested language.
            3. Use your tools (MCP) to save files if a file path is provided.
            """)
        .defaultTools(toolCallbacks) // Cast to Object[] to satisfy the varargs if needed
        .build();
}

}
