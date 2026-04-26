package com.codeanalyzer.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class FsdService {

public String extractTextFromMultipleBase64(List<String> base64Pdfs) {
        return base64Pdfs.stream()
                .map(base64 -> {
                    try {
                        // 1. Decode and create resource
                        byte[] fileBytes = Base64.getDecoder().decode(base64.replaceAll("\\s", ""));
                        ByteArrayResource resource = new ByteArrayResource(fileBytes);
                        
                        // 2. TikaDocumentReader handles PDF, Word, and Text automatically
                        TikaDocumentReader reader = new TikaDocumentReader(resource);
                        
                        // 3. Extract text into Spring AI Documents
                        List<Document> docs = reader.get();
                        
                        return docs.isEmpty() ? "[Document is empty]" : 
                               docs.stream()
                                   .map(Document::getText)
                                   .collect(Collectors.joining("\n"));
                    } catch (Exception e) {
                        return "[Extraction Error: " + e.getMessage() + "]";
                    }
                })
                .collect(Collectors.joining("\n\n--- NEXT DOCUMENT ---\n\n"));
    }

}

