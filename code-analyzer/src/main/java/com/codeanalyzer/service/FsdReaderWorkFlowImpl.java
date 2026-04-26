package com.codeanalyzer.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class FsdReaderWorkFlowImpl  implements FsdReaderWorkFlow {

    private final ChatClient analystAgent;
    private final ChatClient developerAgent;
    private final FsdService fsdService; // Your existing service for PDF extraction

    public FsdReaderWorkFlowImpl(ChatClient analystAgent, ChatClient developerAgent, FsdService fsdService) {
        this.analystAgent = analystAgent;
        this.developerAgent = developerAgent;
        this.fsdService = fsdService;
    }


    @Override
    public Flux<String> runWorkflow(List<String> files, String prompt) {
        // Step 1: Extract and aggregate text from all FSDs
    String combinedFsdText = fsdService.extractTextFromMultipleBase64(files);

    // Step 2: Analyst Agent processes the aggregated document
    String technicalDesign = analystAgent.prompt()
            .user("Analyze these related FSD documents and create a unified technical design: " + combinedFsdText)
            .call()
            .content();

    // Step 3: Developer Agent generates final code
    return developerAgent.prompt()
            .user(u -> u.text("Technical Design: {design}\nPreference: {pref}")
                .param("design", technicalDesign)
                .param("pref", prompt))
             .stream()
            .content();
    }
    
}
