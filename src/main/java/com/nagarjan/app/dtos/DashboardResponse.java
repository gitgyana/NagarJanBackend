package com.nagarjan.app.dtos;

import java.util.List;
import java.util.Map;

public record DashboardResponse(
        List<LabelCardDTO> labels,
        List<ProgressCardDTO> categoryData,
        List<Map<String, Object>> classificationTable
) {}