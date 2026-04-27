package com.ocr.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OcrReader {

    private final String API_URL = "https://api.ocr.space/parse/image"; // ✅ Fixed endpoint
    private final RestClient restClient = RestClient.create();
    private final String API_KEY = "K82073165388957";
    private final Path tempDir = Paths.get("temp-uploads");

    public List<String> saveBase64ToTemp(List<String> base64Documents) throws IOException {
        if (!Files.exists(tempDir)) Files.createDirectories(tempDir);
        List<String> savedPaths = new ArrayList<>();

        for (String base64Data : base64Documents) {
            if (base64Data == null || base64Data.isEmpty()) continue;

            String[] parts = base64Data.split(",");
            String pureBase64 = parts.length > 1 ? parts[1] : parts[0];
            byte[] decodedBytes = Base64.getDecoder().decode(pureBase64.trim().replaceAll("\\s", ""));

            String extension = base64Data.contains("pdf") ? ".pdf" : ".jpg";
            Path filePath = tempDir.resolve(UUID.randomUUID().toString() + extension);

            Files.write(filePath, decodedBytes);
            savedPaths.add(filePath.toAbsolutePath().toString());
        }
        return savedPaths;
    }

    public String processAndCleanup(List<String> filePaths) {
        List<String> allExtractedText = new ArrayList<>();

        for (String path : filePaths) {
            File file = new File(path);
            if (!file.exists()) continue;

            try {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("apikey", API_KEY);
                body.add("file", new FileSystemResource(file));
                body.add("OCREngine", "2");
                body.add("isOverlayRequired", "false"); // ✅ Reduces response size

                String rawResponse = restClient.post()
                        .uri(API_URL)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .body(String.class);

                System.out.println("DEBUG API RESPONSE: " + rawResponse);

                if (rawResponse != null && rawResponse.contains("ParsedText")) {
                    allExtractedText.add("File: " + file.getName() + "\n" + rawResponse);
                } else {
                    allExtractedText.add("API Error for " + file.getName() + ": " + rawResponse);
                }

            } catch (Exception e) {
                allExtractedText.add("Request Error for " + file.getName() + ": " + e.getMessage());
            } finally {
                try {
                    Files.deleteIfExists(file.toPath());
                } catch (IOException e) {
                    System.err.println("Failed to delete: " + path);
                }
            }
        }
        return String.join("\n\n---\n\n", allExtractedText);
    }
}

