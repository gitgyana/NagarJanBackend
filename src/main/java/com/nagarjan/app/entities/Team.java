package com.nagarjan.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "team_name", nullable = false, length = 150)
    private String teamName;

    @Column(name = "team_contact", length = 20)
    private String teamContact;
}