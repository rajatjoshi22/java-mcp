package com.codeanalyzer.Model;

import java.util.List;

public class FsdRequest {
    private List<String> base64Content;
    private String userPrompt;

    public List<String> getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(List<String> base64Content) {
        this.base64Content = base64Content;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }
    
}
