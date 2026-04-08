CREATE TABLE area_statistics
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_id         BIGINT,
    active_grievances   INT,
    resolved_grievances INT,
    avg_resolution_time FLOAT,
    last_updated        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES locations (location_id)
);

CREATE TABLE heatmap_data
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_id    BIGINT,
    category_id    BIGINT,
    incident_count INT,
    density_score  DECIMAL(6, 2),
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);
