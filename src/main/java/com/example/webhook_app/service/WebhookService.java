package com.example.webhook_app.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.webhook_app.dto.GenerateWebhookRequest;
import com.example.webhook_app.dto.GenerateWebhookResponse;
import com.example.webhook_app.dto.TestWebhookRequest;

@Service
public class WebhookService {

    private final RestTemplate restTemplate;

    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processWebhook() {
        try {
            // Step 1: Generate webhook
            GenerateWebhookResponse response = generateWebhook();
            if (response == null) {
                System.err.println("Failed to generate webhook");
                return;
            }

            System.out.println("Webhook URL: " + response.getWebhook());
            System.out.println("Access Token: " + response.getAccessToken());

            // Step 2: Solve SQL problem based on regNo
            String sqlQuery = solveSQLProblem();

            // Step 3: Submit solution to webhook URL
            submitSolution(response.getWebhook(), response.getAccessToken(), sqlQuery);

        } catch (Exception e) {
            System.err.println("Error processing webhook: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private GenerateWebhookResponse generateWebhook() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        
        GenerateWebhookRequest request = new GenerateWebhookRequest(
            "Janmesh Raut",
            "ADT23SOCB1593",
            "janmeshraut11@gmail.com"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GenerateWebhookRequest> entity = new HttpEntity<>(request, headers);

        try {
            return restTemplate.postForObject(url, entity, GenerateWebhookResponse.class);
        } catch (Exception e) {
            System.err.println("Error generating webhook: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String solveSQLProblem() {

        return "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, YEAR(CURDATE()) - YEAR(e.DOB) - (DATE_FORMAT(CURDATE(), '%m%d') < DATE_FORMAT(e.DOB, '%m%d')) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) != 1 ORDER BY p.AMOUNT DESC LIMIT 1;";
    }

    private void submitSolution(String webhookUrl, String accessToken, String sqlQuery) {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
        TestWebhookRequest request = new TestWebhookRequest(sqlQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        HttpEntity<TestWebhookRequest> entity = new HttpEntity<>(request, headers);

        try {
            String response = restTemplate.postForObject(url, entity, String.class);
            System.out.println("Solution submitted successfully. Response: " + response);
        } catch (Exception e) {
            System.err.println("Error submitting solution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
