package com.nagarjan.app.services;

import com.nagarjan.app.entities.GrievanceClassification;
import com.nagarjan.app.entities.Grievances;
import com.nagarjan.app.entities.GrievancesRaw;
import com.nagarjan.app.repositories.GrievanceClassificationRepository;
import com.nagarjan.app.repositories.GrievancesRawRepository;
import com.nagarjan.app.repositories.GrievancesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InboundFeedService {

    private final GrievancesRawRepository rawRepo;
    private final GrievancesRepository grievanceRepo;
    private final GrievanceClassificationRepository classificationRepo;

    public List<Map<String, Object>> getInboundFeed() {

        List<GrievancesRaw> raws = rawRepo.findAll();

        return raws.stream().map(raw -> {
            Map<String, Object> map = new HashMap<>();

            map.put("dataId", raw.getRawId());
            map.put("source", raw.getSource());
            map.put("grievanceId", "GR-" + raw.getRawId());
            map.put("timestamp", raw.getTimestamp());
            map.put("rawContent", raw.getRawContent());
            map.put("status", raw.getStatus());

            // Optional category mapping
            Optional<Grievances> grievance = grievanceRepo.findByRaw_RawId(raw.getRawId());
            grievance.ifPresent(g -> {
                Optional<GrievanceClassification> cls =
                        classificationRepo.findTopByGrievance(g);

                cls.ifPresent(c ->
                        map.put("category", c.getCategory().getName())
                );
            });

            map.put("action", "VIEW");

            return map;
        }).toList();
    }

    public List<Map<String, Object>> getIngestDetails(Long id) {

        return List.of(
                Map.of("label", "Transcription", "value", 80),
                Map.of("label", "Classification", "value", 60),
                Map.of("label", "Deduplication", "value", 40)
        );
    }
}
