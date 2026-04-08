package com.nagarjan.app.services;

import com.nagarjan.app.entities.enums.ClassificationStatus;
import com.nagarjan.app.entities.enums.WorkOrderStatus;
import com.nagarjan.app.repositories.CategoryRepository;
import com.nagarjan.app.repositories.GrievanceClassificationRepository;
import com.nagarjan.app.repositories.GrievancesRepository;
import com.nagarjan.app.repositories.WorkOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GrievancesRepository grievanceRepo;
    private final GrievanceClassificationRepository classificationRepo;
    private final WorkOrdersRepository workOrderRepo;
    private final CategoryRepository categoryRepo;

    public Map<String, Object> getDashboardData() {

        Map<String, Object> response = new HashMap<>();

        long totalInbound = grievanceRepo.count();
        long autoClassified = classificationRepo.countByStatus(ClassificationStatus.valueOf("AUTO"));
        long pendingWorkOrders = workOrderRepo.countByStatus(WorkOrderStatus.valueOf("ASSIGNED"));

        Map<String, Object> labels = Map.of(
                "totalInbound", totalInbound,
                "autoClassified", autoClassified,
                "pendingWorkOrders", pendingWorkOrders
        );

        // Category Distribution
        List<Object[]> categoryData = categoryRepo.getCategoriesByCount();

        List<Map<String, Object>> classificationTable =
                classificationRepo.getDashboardClassificationData();

        response.put("labels", labels);
        response.put("categoryData", categoryData);
        response.put("classificationTable", classificationTable);

        return response;
    }
}
