-- users
INSERT INTO users (name, phone, email, role)
VALUES ('Amit Sharma', '9000000001', 'amit@mail.com', 'CITIZEN'),
       ('Neha Verma', '9000000002', 'neha@mail.com', 'CITIZEN'),
       ('Admin User', '9000000003', 'admin@mail.com', 'ADMIN'),
       ('Operator One', '9000000004', 'operator@mail.com', 'OPERATOR');


-- departments
INSERT INTO departments (name, description, contact_number)
VALUES ('Water Supply', 'Handles water issues', '1111111111'),
       ('Road Maintenance', 'Road and pothole repairs', '2222222222'),
       ('Electricity', 'Power related issues', '3333333333'),
       ('Sanitation', 'Waste & drainage', '4444444444');


-- teams
INSERT INTO teams (department_id, team_name, team_contact)
VALUES (1, 'Water Team A', '8000000001'),
       (2, 'Road Repair Squad', '8000000002'),
       (3, 'Electric Grid Team', '8000000003'),
       (4, 'Sanitation Crew', '8000000004');


-- locations
INSERT INTO locations (area_name, ward_number, city, state, latitude, longitude, population_density)
VALUES ('Salt Lake Sector V', 'W1', 'Kolkata', 'WB', 22.5726, 88.3639, 12000),
       ('Park Street', 'W2', 'Kolkata', 'WB', 22.5540, 88.3500, 15000),
       ('Howrah Bridge Area', 'W3', 'Kolkata', 'WB', 22.5850, 88.3468, 18000);


-- grievances raw
INSERT INTO grievances_raw (source, external_reference_id, raw_content, media_text_content, timestamp, location_id,
                            status)
VALUES ('APP', 'EXT-1001', 'Water leakage near street', 'audio transcript...', NOW(), 1, 'INGESTED'),
       ('EMAIL', 'EXT-1002', 'Big pothole on main road', 'email content...', NOW(), 2, 'INGESTED'),
       ('IVR', 'EXT-1003', 'No electricity since morning', 'voice transcript...', NOW(), 3, 'INGESTED'),
       ('SOCIAL', 'EXT-1004', 'Garbage not collected', 'tweet content...', NOW(), 1, 'INGESTED');


-- grievances
INSERT INTO grievances (raw_id, title, description, citizen_id, location_id, status)
VALUES (1, 'Water Leakage', 'Pipe burst causing water loss', 1, 1, 'OPEN'),
       (2, 'Pothole Issue', 'Huge pothole damaging vehicles', 2, 2, 'OPEN'),
       (3, 'Power Outage', 'Electricity gone for hours', 1, 3, 'OPEN'),
       (4, 'Garbage Issue', 'Garbage piling up', 2, 1, 'OPEN');


-- grievance duplicates
INSERT INTO grievance_duplicates (grievance_id, duplicate_of, similarity_score, method)
VALUES (4, 1, 78.50, 'AI');


-- grievance clusters
INSERT INTO grievance_clusters (primary_grievance_id, cluster_size)
VALUES (1, 2);


-- cluster mapping
INSERT INTO cluster_mapping (cluster_id, grievance_id)
VALUES (1, 1),
       (1, 4);


-- categories
INSERT INTO categories (name, department_id)
VALUES ('Water Supply', 1),
       ('Roads', 2),
       ('Electricity', 3),
       ('Sanitation', 4);


-- grievance classification
INSERT INTO grievance_classification (grievance_id, category_id, confidence_score, model_version, status)
VALUES (1, 1, 95.50, 'v1', 'AUTO'),
       (2, 2, 92.10, 'v1', 'AUTO'),
       (3, 3, 97.00, 'v1', 'AUTO'),
       (4, 4, 88.00, 'v1', 'MANUAL_REVIEW');


-- classification logs
INSERT INTO classification_logs (grievance_id, raw_output, model_latency_ms)
VALUES (1, '{
  "category": "Water"
}', 120),
       (2, '{
         "category": "Road"
       }', 140),
       (3, '{
         "category": "Electricity"
       }', 110),
       (4, '{
         "category": "Sanitation"
       }', 160);


-- priority scores
INSERT INTO priority_scores (grievance_id, severity_score, recurrence_score, age_score, density_score, final_score)
VALUES (1, 90, 70, 60, 80, 75.00),
       (2, 85, 65, 55, 75, 70.00),
       (3, 95, 80, 70, 85, 82.00),
       (4, 70, 60, 50, 65, 61.00);


-- priority factors
INSERT INTO priority_factors (grievance_id, factor_name, factor_value, weight)
VALUES (1, 'SEVERITY', 90, 0.4),
       (1, 'DENSITY', 80, 0.3),
       (2, 'SEVERITY', 85, 0.4),
       (3, 'SEVERITY', 95, 0.4);


-- work orders
INSERT INTO work_orders (grievance_id, priority_score, assigned_team_id, status)
VALUES (3, 82.00, 3, 'ASSIGNED'),
       (1, 75.00, 1, 'ASSIGNED'),
       (2, 70.00, 2, 'IN_PROGRESS'),
       (4, 61.00, 4, 'ASSIGNED');


-- work order logs
INSERT INTO work_order_logs (work_order_id, action, remarks, updated_by)
VALUES (1, 'ASSIGNED', 'Assigned to electricity team', 3),
       (2, 'ASSIGNED', 'Water team dispatched', 3),
       (3, 'STARTED', 'Repair work started', 4);


-- area statistics
INSERT INTO area_statistics (location_id, active_grievances, resolved_grievances, avg_resolution_time)
VALUES (1, 5, 10, 2.5),
       (2, 3, 8, 3.2),
       (3, 6, 12, 1.8);


-- heatmap data
INSERT INTO heatmap_data (location_id, category_id, incident_count, density_score)
VALUES (1, 1, 10, 85.5),
       (2, 2, 7, 75.2),
       (3, 3, 12, 90.1);


--
SELECT g.grievance_id, g.title, ps.final_score
FROM grievances g
         JOIN priority_scores ps ON g.grievance_id = ps.grievance_id
ORDER BY ps.final_score DESC;
