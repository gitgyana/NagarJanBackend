CREATE TABLE grievances_raw
(
    raw_id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    source                ENUM ('APP','EMAIL','IVR','SOCIAL') NOT NULL,
    external_reference_id VARCHAR(100),
    raw_content           TEXT,
    media_text_content    LONGTEXT,
    timestamp             DATETIME,
    location_id           BIGINT,
    status                ENUM ('INGESTED','PROCESSING','FAILED') DEFAULT 'INGESTED',
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    INDEX idx_source_time (source, timestamp)
);

CREATE TABLE grievances
(
    grievance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    raw_id       BIGINT,
    title        VARCHAR(255),
    description  TEXT,
    citizen_id   BIGINT NULL,
    location_id  BIGINT,
    created_at   TIMESTAMP                              DEFAULT CURRENT_TIMESTAMP,
    status       ENUM ('OPEN','IN_PROGRESS','RESOLVED') DEFAULT 'OPEN',
    FOREIGN KEY (raw_id) REFERENCES grievances_raw (raw_id),
    FOREIGN KEY (citizen_id) REFERENCES users (user_id),
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    INDEX idx_status (status),
    INDEX idx_location (location_id)
);
