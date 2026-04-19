package com.travelling.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelling.Model.TravelModel;
import com.travelling.service.TravelToolService;


@RestController
@RequestMapping("/api/travelling")
public class TravelItenary {
        private final ChatClient chatClient;

    public TravelItenary(ChatClient.Builder builder, TravelToolService travelTools) {
        this.chatClient = builder
            .defaultSystem("You are a travel assistant. Use tools to find real-time info.")
            .defaultTools(travelTools) 
            .build();
    }

    @PostMapping("/plan")
    public String planTrip(@RequestBody TravelModel travelModel) {
        return chatClient.prompt()
            .user(travelModel.getMessage())
            .call()
            .content();
    }
}
