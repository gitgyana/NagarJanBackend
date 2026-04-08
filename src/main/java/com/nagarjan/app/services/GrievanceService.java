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

        Location location = locationRepo.findById(request.locationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Users citizen = null;
        if (request.citizenId() != null) {
            citizen = usersRepo.findById(request.citizenId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        GrievancesRaw raw = GrievancesRaw.builder()
                .source(mapSource(request.source()))
                .rawContent(request.content())
                .mediaTextContent(request.content())
                .timestamp(LocalDateTime.now())
                .location(location)
                .status(GrievanceStatusRaw.INGESTED)
                .build();

        raw = rawRepo.save(raw);

        Grievances grievance = Grievances.builder()
                .raw(raw)
                .title(request.title())
                .description(request.content())
                .citizen(citizen)
                .location(location)
                .status(GrievanceStatus.OPEN)
                .build();

        grievanceRepo.save(grievance);
    }

    private GrievanceSource mapSource(String source) {
        return switch (source.toUpperCase()) {
            case "TEXT" -> GrievanceSource.APP;
            case "EMAIL" -> GrievanceSource.EMAIL;
            case "PHONE" -> GrievanceSource.IVR;
            default -> GrievanceSource.APP;
        };
    }
}