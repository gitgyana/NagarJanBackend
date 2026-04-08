CREATE TABLE grievance_duplicates
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    duplicate_of     BIGINT,
    similarity_score DECIMAL(5, 2),
    method           ENUM ('AI','RULE_BASED'),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    FOREIGN KEY (duplicate_of) REFERENCES grievances (grievance_id),
    INDEX idx_dup (grievance_id, duplicate_of)
);

CREATE TABLE grievance_clusters
(
    cluster_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    primary_grievance_id BIGINT,
    cluster_size         INT       DEFAULT 1,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (primary_grievance_id) REFERENCES grievances (grievance_id)
);

CREATE TABLE cluster_mapping
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    cluster_id   BIGINT,
    grievance_id BIGINT,
    FOREIGN KEY (cluster_id) REFERENCES grievance_clusters (cluster_id),
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    UNIQUE KEY uniq_cluster (cluster_id, grievance_id)
);
