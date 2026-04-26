package com.codeanalyzer.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class FsdService {

public String extractTextFromMultipleBase64(List<String> base64Pdfs) {
    return base64Pdfs.stream()
            .<String>map(base64 -> { // Add <String> type witness here
                byte[] pdfBytes = Base64.getDecoder().decode(base64);
                PagePdfDocumentReader reader = new PagePdfDocumentReader(new ByteArrayResource(pdfBytes));
                
                // Explicitly stream and join
                return reader.get().stream()
                        .map(Document::getText) // Use getText() for M6
                        .collect(Collectors.joining("\n"));
            })
            .collect(Collectors.joining("\n\n--- NEXT DOCUMENT ---\n\n"));
}
}
