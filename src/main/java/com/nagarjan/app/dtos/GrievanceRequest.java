package com.nagarjan.app.dtos;

public record GrievanceRequest(
        String source,
        String title,
        String content,
        Long locationId
) {
}