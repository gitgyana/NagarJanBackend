package com.nagarjan.app.dtos;

public record GeminiResponseDTO(
        String category,
        String title,
        String language,
        double confidence
) {}