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
                You are an Advanced Execution Agent. 
                Your goal is to transform specifications or instructions into final deliverables.
                1. Process the input provided and generate high-quality, structured output.
                2. Adhere to the specific standards, syntax, or formatting requested by the user.
                3. Use your available tools (MCP) to interact with the environment, save files, or perform actions as needed.
                4. Verify that the output is complete and ready for use.
                """)
            .defaultTools(toolCallbacks.toArray()) 
            .build();
}

}
