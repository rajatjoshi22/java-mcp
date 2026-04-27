package com.ocr.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocr.Model.OcrRequest;
import com.ocr.service.OcrFlow;

import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*", methods = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
public class OcrController {

    private final OcrFlow ocrFlow;

    public OcrController(OcrFlow ocrFlow) {
        this.ocrFlow = ocrFlow;

    }

    @PostMapping("/ocr/read")
    public Flux<String>  generateCode(@RequestBody OcrRequest request) {
        // Request object should contain 'base64Content' and 'userPrompt'
        return ocrFlow.processOcrRequest(request.getUserquerString(), request.getImageUrls());
      
    }

}
