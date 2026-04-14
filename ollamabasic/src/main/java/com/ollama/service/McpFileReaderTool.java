package com.ollama.service;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;



@Service
public class McpFileReaderTool {
    
    @Tool(name = "analyze_Pdf", description = "Reads the content of a PDF file and answers questions about it.")
    public String analyzePdf(String filePath) {
     Resource pdfResource = new FileSystemResource(filePath);
     PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
     return reader.read().stream().map(doc -> doc.getText()).collect(Collectors.joining("\n"));
    }
}
