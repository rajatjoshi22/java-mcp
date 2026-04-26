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
                You are a Senior Technical Analyst. 
                Your goal is to analyze Functional Specification Documents (FSDs).
                1. Extract core business logic and functional requirements.
                2. Identify all required entities, services, and API endpoints.
                3. Create a structured technical design document that a developer can follow.
                """)
            .build();
    }
}