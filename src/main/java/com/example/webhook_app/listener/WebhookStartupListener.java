package com.example.webhook_app.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.webhook_app.service.WebhookService;

@Component
public class WebhookStartupListener {

    private final WebhookService webhookService;

    public WebhookStartupListener(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStart() {
        System.out.println("Application started. Processing webhook...");
        webhookService.processWebhook();
    }
}
