package com.study.profile_stack_api.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private String position;

    @Column(name="career_years", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer careerYears;

    @Column(name="github_url", length = 200)
    private String githubUrl;

    @Column(name="blog_url", length = 200)
    private String blogUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
