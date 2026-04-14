package com.ollama.controller;

import com.ollama.Model.McpToolModel;
import com.ollama.service.McpToolService;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpToolController {

    @Autowired
    private  McpToolService mcpToolService;

     @PostMapping(value = "/api/analyze-pdf-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> askAboutPdf(
            @RequestBody McpToolModel request) {
        
        // This calls your service, which tells Ollama to use the MCP tool
        return mcpToolService.analyzePdf(request);
    }
}
