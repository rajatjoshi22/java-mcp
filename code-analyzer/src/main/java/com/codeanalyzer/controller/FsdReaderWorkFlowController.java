package com.codeanalyzer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeanalyzer.Model.FsdRequest;
import com.codeanalyzer.service.FsdReaderWorkFlow;

import reactor.core.publisher.Flux;

@RestController
@CrossOrigin( origins = "*", allowedHeaders = "*")
public class FsdReaderWorkFlowController {
      private final FsdReaderWorkFlow service;

    public FsdReaderWorkFlowController(FsdReaderWorkFlow service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public Flux<String>  generateCode(@RequestBody FsdRequest request) {
        // Request object should contain 'base64Content' and 'userPrompt'
        return service.runWorkflow(request.getBase64Content(), request.getUserPrompt());
      
    }
}
