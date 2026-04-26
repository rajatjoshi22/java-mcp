package com.codeanalyzer.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalystAgentConfig {

    @Bean
    public ChatClient analystAgent(ChatClient.Builder builder) {
      return builder
            .defaultSystem("""
                You are a versatile Document Analysis Expert. 
                Your goal is to process the provided document and extract actionable insights:
                1. Identify the primary purpose and key themes of the text.
                2. Extract critical data points, requirements, or entities mentioned.
                3. Summarize the content into a structured, logical format suitable for the user's context.
                4. Highlight any dependencies, risks, or follow-up actions required.
                
                Please adapt your tone and depth based on the document type provided.
                """)
            .build();
    }
}