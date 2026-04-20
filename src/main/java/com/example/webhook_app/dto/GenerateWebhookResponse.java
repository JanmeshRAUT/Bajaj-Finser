package com.example.webhook_app.dto;

public class GenerateWebhookResponse {
    private String webhook;
    private String accessToken;
    private int userId;

    public GenerateWebhookResponse() {
    }

    public GenerateWebhookResponse(String webhook, String accessToken, int userId) {
        this.webhook = webhook;
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
