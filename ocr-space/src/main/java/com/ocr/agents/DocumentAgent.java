package com.ocr.agents;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.modelcontextprotocol.client.McpSyncClient;

@Configuration
public class DocumentAgent {

    @Bean
    public ChatClient createDocumentAgent(ChatClient.Builder builder , List<McpSyncClient> clients) {
         // Correct way: use McpToolUtils to get ToolCallbacks from sync clients
        List<org.springframework.ai.tool.ToolCallback> toolCallbacks = 
                McpToolUtils.getToolCallbacksFromSyncClients(clients);

       return builder
            .defaultSystem("""
                You are a Document Processing Agent. 
                Your goal is to process documents based on user queries and extract relevant information.
                1. Analyze the user query to understand what information is being requested.
                2. Use your available tools (MCP) to access and process the documents as needed.
                3. Extract and structure the information according to the user's request.
                4. Ensure that the output is clear, concise, and directly addresses the user's query.
                """)
            .defaultTools(toolCallbacks.toArray())
            .build();
    }


}
