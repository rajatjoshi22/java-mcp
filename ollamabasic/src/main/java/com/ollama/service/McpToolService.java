package com.ollama.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.ollama.Model.McpToolModel;

import reactor.core.publisher.Flux;



@Service
public class McpToolService {    
    
    private final ChatClient chatClient;

    @Autowired
    private McpFileReaderTool reader;

    public McpToolService(ChatClient.Builder builder, ToolCallbackProvider toolProvider) {
     // In M6, we fetch tools through the listTools() method
    var tools = toolProvider.getToolCallbacks();
        System.out.println("DEBUG: Number of MCP tools found: " + tools.length);
        this.chatClient = builder
                .build();
    }

    ///create a stream of data to send the output
    public Flux<String> analyzePdf( McpToolModel request) {
    //read pdf contect
    String pdfContent  = this.reader.analyzePdf(request.getFilePath());  
    return chatClient.prompt()
            //.tools("analyze_Pdf")
            .user(u -> u.text("The content of the PDF is: " + pdfContent + "\n\nNow, answer the following question based on the PDF content: " + request.getQuestion()))
            .stream()
            .content();
    }
}

