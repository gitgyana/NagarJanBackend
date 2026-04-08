package com.nagarjan.app.dtos;

public record CreateGrievanceRequest(
        String source,
        String title,
        String content,
        Long locationId,
        Long citizenId
) {}