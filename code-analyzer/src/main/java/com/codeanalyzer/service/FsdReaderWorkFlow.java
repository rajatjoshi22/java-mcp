package com.codeanalyzer.service;

import java.util.List;

import reactor.core.publisher.Flux;

public interface FsdReaderWorkFlow {

    Flux<String> runWorkflow(List<String> files  ,String prompt);
    
}
