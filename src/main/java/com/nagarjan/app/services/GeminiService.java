package com.nagarjan.app.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarjan.app.dtos.GeminiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiResponseDTO analyzeText(String content) {

        int retries = 2;

        while (retries-- > 0) {
            try {
                return callGemini(content);
            } catch (Exception e) {

                // Retry only on 503
                if (e.getMessage() != null && e.getMessage().contains("503")) {
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                    continue;
                }

                break;
            }
        }

        return fallback(content);
    }

    private GeminiResponseDTO callGemini(String content) throws Exception {

        String prompt = """
            Return ONLY valid JSON.

            {
              "category": "WATER | ROADS | ELECTRICITY | SANITATION | UNKNOWN",
              "title": "short title",
              "language": "English | Hindi | Odia",
              "confidence": number
            }

            Text: %s
            """.formatted(content);

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        String response = restTemplate.postForObject(url, requestBody, String.class);

        JsonNode root = objectMapper.readTree(response);

        String textResponse = root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        textResponse = textResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        return objectMapper.readValue(textResponse, GeminiResponseDTO.class);
    }

    private GeminiResponseDTO fallback(String content) {

        String text = content.toLowerCase();

        if (text.contains("water") || text.contains("pipe") || text.contains("leak")) {
            return new GeminiResponseDTO("WATER", "Water Issue Reported", "English", 0.6);
        }

        if (text.contains("road") || text.contains("pothole")) {
            return new GeminiResponseDTO("ROADS", "Road Issue Reported", "English", 0.6);
        }

        if (text.contains("electric") || text.contains("power") || text.contains("light")) {
            return new GeminiResponseDTO("ELECTRICITY", "Electricity Issue", "English", 0.6);
        }

        if (text.contains("garbage") || text.contains("waste")
                || text.contains("dirty") || text.contains("clean")
                || text.contains("not cleaned") || text.contains("drain")) {

            return new GeminiResponseDTO("SANITATION", "Sanitation Issue", "English", 0.6);
        }

        return new GeminiResponseDTO("UNKNOWN", "General Issue", "English", 0.3);
    }
}