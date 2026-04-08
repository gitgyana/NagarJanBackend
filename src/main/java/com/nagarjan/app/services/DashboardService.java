package com.nagarjan.app.services;

import com.nagarjan.app.dtos.DashboardResponse;
import com.nagarjan.app.dtos.LabelCardDTO;
import com.nagarjan.app.dtos.ProgressCardDTO;
import com.nagarjan.app.entities.enums.ClassificationStatus;
import com.nagarjan.app.entities.enums.WorkOrderStatus;
import com.nagarjan.app.repositories.CategoryRepository;
import com.nagarjan.app.repositories.GrievanceClassificationRepository;
import com.nagarjan.app.repositories.GrievancesRepository;
import com.nagarjan.app.repositories.WorkOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GrievancesRepository grievanceRepo;
    private final GrievanceClassificationRepository classificationRepo;
    private final WorkOrdersRepository workOrderRepo;
    private final CategoryRepository categoryRepo;

    public DashboardResponse getDashboardData() {

        long totalInbound = grievanceRepo.count();
        long autoClassified = classificationRepo.countByStatus(ClassificationStatus.AUTO);
        long pendingWorkOrders = workOrderRepo.countByStatus(WorkOrderStatus.ASSIGNED);

        List<LabelCardDTO> labels = List.of(
                new LabelCardDTO("Total Inbound (Today)", String.valueOf(totalInbound), "data"),
                new LabelCardDTO("Auto-classified", String.valueOf(autoClassified), "classification"),
                new LabelCardDTO("Pending Work-Orders", String.valueOf(pendingWorkOrders), "pending")
        );

        List<ProgressCardDTO> categoryData = categoryRepo.getCategoriesByCount()
                .stream()
                .map(obj -> new ProgressCardDTO(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .toList();

        List<Map<String, Object>> classificationTable =
                classificationRepo.getDashboardClassificationData()
                        .stream()
                        .map(obj -> {

                            long manualCount = ((Number) obj.get("manual_count")).longValue();
                            double confidence = ((Number) obj.get("avg_confidence")).doubleValue();

                            return Map.of(
                                    "dataId", UUID.randomUUID().toString(),
                                    "source", "System",
                                    "grievanceId", "GRV-" + UUID.randomUUID().toString().substring(0, 6),
                                    "confidence", confidence,
                                    "category", obj.get("category"),
                                    "status", manualCount > 0 ? "MANUAL" : "AUTO",
                                    "action", "View"
                            );
                        })
                        .toList();

        return new DashboardResponse(labels, categoryData, classificationTable);
    }
}