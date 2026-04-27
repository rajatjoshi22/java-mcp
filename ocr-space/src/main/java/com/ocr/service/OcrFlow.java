package com.ocr.service;

import java.util.List;

import reactor.core.publisher.Flux;

public interface OcrFlow {

    Flux<String> processOcrRequest(String userQuery, List<String> imageUrls);

}
