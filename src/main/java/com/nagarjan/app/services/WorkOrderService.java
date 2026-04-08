package com.nagarjan.app.services;

import com.nagarjan.app.entities.Grievances;
import com.nagarjan.app.entities.Team;
import com.nagarjan.app.entities.WorkOrders;
import com.nagarjan.app.repositories.GrievancesRepository;
import com.nagarjan.app.repositories.PriorityScoresRepository;
import com.nagarjan.app.repositories.TeamRepository;
import com.nagarjan.app.repositories.WorkOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrdersRepository workOrderRepo;
    private final PriorityScoresRepository priorityRepo;
    private final GrievancesRepository grievanceRepo;
    private final TeamRepository teamRepo;

    public Map<String, Object> getWorkOrders() {

        List<WorkOrders> orders = workOrderRepo.findAll();

        List<Map<String, Object>> records = new ArrayList<>();

        int rank = 1;

        for (WorkOrders order : orders) {

            Map<String, Object> map = new HashMap<>();

            Grievances grievance = order.getGrievance();
            Team team = order.getAssignedTeam();

            map.put("rank", String.format("%02d", rank++));
            map.put("grievanceId", "GR-" + grievance.getGrievanceId());
            map.put("grievanceType", grievance.getTitle());
            map.put("priorityScore", order.getPriorityScore());
            map.put("assignedTeam", team.getTeamName());
            map.put("assignedTeamContact", team.getTeamContact());

            records.add(map);
        }

        Map<String, Object> response = new HashMap<>();

        response.put("topCriticalDensity", Map.of(
                "areaName", "Salt Lake Sector V",
                "description", "High complaint density"
        ));

        response.put("records", records);

        return response;
    }
}
