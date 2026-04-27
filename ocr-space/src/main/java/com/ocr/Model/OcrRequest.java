package com.ocr.Model;

import java.util.List;

public class OcrRequest {

    private List<String> imageUrls;

    private String userquerString;
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getUserquerString() {
        return userquerString;
    }

    public void setUserquerString(String userquerString) {
        this.userquerString = userquerString;
    }
}
