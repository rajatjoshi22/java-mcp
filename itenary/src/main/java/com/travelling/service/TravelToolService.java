package com.travelling.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.Content;

import java.util.List;
import java.util.Map;

@Service
public class TravelToolService {

    private final List<McpSyncClient> mcpClients;

    public TravelToolService(List<McpSyncClient> mcpClients) {
        this.mcpClients = mcpClients;
    }

    @Tool(description = "Get real-time tourist attractions, timings, and prices for any city")
    public String getAttractions(String city) {
        // We convert the List<Content> to a String so the LLM can read it easily
        var response = mcpClients.get(0).callTool(new McpSchema.CallToolRequest("duckduckgo_search", 
            Map.of("query", "top tourist attractions and opening hours in " + city)));
        return formatMcpResponse(response.content());
    }

    @Tool(description = "Search for current hotel options and rates in a city")
    public String getHotels(String city) {
        var response = mcpClients.get(0).callTool(new McpSchema.CallToolRequest("duckduckgo_search", 
            Map.of("query", "best hotels and current nightly rates in " + city)));
        return formatMcpResponse(response.content());
    }

    @Tool(description = "Search for real flight prices and schedules between two cities")
    public String getFlightTickets(String sourceCity, String destinationCity) {
        var response = mcpClients.get(0).callTool(new McpSchema.CallToolRequest("duckduckgo_search", 
            Map.of("query", "current flight ticket prices from " + sourceCity + " to " + destinationCity)));
        return formatMcpResponse(response.content());
    }

    // Helper to extract text content for the AI to process
    private String formatMcpResponse(List<Content> contents) {
        return contents.stream()
                .filter(c -> c instanceof McpSchema.TextContent)
                .map(c -> ((McpSchema.TextContent) c).text())
                .reduce("", (a, b) -> a + "\n" + b);
    }
}
