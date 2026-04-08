package com.nagarjan.app.services;

import com.nagarjan.app.dtos.CreateGrievanceRequest;
import com.nagarjan.app.entities.*;
import com.nagarjan.app.entities.enums.*;
import com.nagarjan.app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GrievanceService {

    private final GrievancesRawRepository rawRepo;
    private final GrievancesRepository grievanceRepo;
    private final LocationRepository locationRepo;
    private final UsersRepository usersRepo;

    public void createGrievance(CreateGrievanceRequest request) {

        String content = request.content();

        boolean isSpam = isSpam(content);

        GrievanceType type = detectType(content);

        String generatedTitle = generateTitle(type, content);

        double confidence = (type == GrievanceType.UNKNOWN) ? 0.3 : 0.9;

        Location location = locationRepo.findById(request.locationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Users citizen = null;
        if (request.citizenId() != null) {
            citizen = usersRepo.findById(request.citizenId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        GrievancesRaw raw = GrievancesRaw.builder()
                .source(mapSource(request.source()))
                .rawContent(content)
                .mediaTextContent(content)
                .timestamp(LocalDateTime.now())
                .location(location)
                .status(GrievanceStatusRaw.INGESTED)
                .build();

        raw = rawRepo.save(raw);

        Grievances grievance = Grievances.builder()
                .raw(raw)
                .title(generatedTitle)
                .description(content)
                .citizen(citizen)
                .location(location)
                .status(GrievanceStatus.OPEN)
                .build();

        grievanceRepo.save(grievance);

        System.out.println("TYPE: " + type + " | CONFIDENCE: " + confidence + " | SPAM: " + isSpam);
    }

    private GrievanceSource mapSource(String source) {
        return switch (source.toUpperCase()) {
            case "TEXT" -> GrievanceSource.APP;
            case "EMAIL" -> GrievanceSource.EMAIL;
            case "PHONE" -> GrievanceSource.IVR;
            default -> GrievanceSource.APP;
        };
    }


    private GrievanceType detectType(String content) {

        String text = content.toLowerCase();

        if (text.contains("water") || text.contains("pipe") || text.contains("leak")) {
            return GrievanceType.WATER;
        }

        if (text.contains("road") || text.contains("pothole")) {
            return GrievanceType.ROADS;
        }

        if (text.contains("electric") || text.contains("power") || text.contains("light")) {
            return GrievanceType.ELECTRICITY;
        }

        if (text.contains("garbage") || text.contains("waste") || text.contains("drain")) {
            return GrievanceType.SANITATION;
        }

        return GrievanceType.UNKNOWN;
    }

    private String generateTitle(GrievanceType type, String content) {

        return switch (type) {
            case WATER -> "Water Issue Reported";
            case ROADS -> "Road / Pothole Issue";
            case ELECTRICITY -> "Electricity Problem";
            case SANITATION -> "Sanitation Complaint";
            default -> content.length() > 30
                    ? content.substring(0, 30) + "..."
                    : content;
        };
    }

    private boolean isSpam(String content) {

        if (content == null || content.trim().length() < 5) {
            return true;
        }

        String text = content.toLowerCase();

        if (text.matches(".*(asdf|qwerty|12345|testtest).*")) {
            return true;
        }

        return false;
    }
}