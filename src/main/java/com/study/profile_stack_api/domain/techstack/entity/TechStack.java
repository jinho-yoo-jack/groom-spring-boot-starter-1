package com.study.profile_stack_api.domain.techstack.entity;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="tech_stack")
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String proficiency;

    @Column(name="years_of_exp", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer yearsOfExp;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
