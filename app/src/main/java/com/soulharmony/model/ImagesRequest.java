package com.soulharmony.model;

import java.util.Map;

public class ImagesRequest {
    private String userId;
    private Map<String, String> imagesUrlWithIndex;

    public ImagesRequest(String userId, Map<String, String> imagesUrlWithIndex) {
        this.userId = userId;
        this.imagesUrlWithIndex = imagesUrlWithIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getImagesUrlWithIndex() {
        return imagesUrlWithIndex;
    }

    public void setImagesUrlWithIndex(Map<String, String> imagesUrlWithIndex) {
        this.imagesUrlWithIndex = imagesUrlWithIndex;
    }
}
