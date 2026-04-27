package com.ocr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.ocr.agents.DocumentAgent;

import reactor.core.publisher.Flux;

@Service
public class OcrFlowImpl implements OcrFlow {

    private final ChatClient documentAgent;

    private final OcrReader ocrReader;

    public OcrFlowImpl(ChatClient documentAgent , OcrReader ocrReader) {
        this.documentAgent = documentAgent;
        this.ocrReader = ocrReader;
    }

    @Override
    public Flux<String> processOcrRequest(String userQuery, List<String> imageUrls) {
      String ocrResults;
      try {
        ocrResults = this.ocrReader.processAndCleanup(this.ocrReader.saveBase64ToTemp(imageUrls));
         return documentAgent.prompt()
                .user(u -> u.text("Here are the OCR results from the documents: {ocr}\n\nUser Query: {query}")
                    .param("ocr", ocrResults)
                    .param("query", userQuery))
                 .stream()
                 .content();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     
      return Flux.error(new RuntimeException("Failed to process OCR request"));
      
    }

}
