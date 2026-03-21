package com.study.profile_stack_api.domain.tech_stack.entity;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tech_stack")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Proficiency proficiency;

    @Column(name = "years_of_exp", nullable = false)
    private Integer yearsOfExp;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public void update(String name, Category category,
                       Proficiency proficiency, Integer yearsOfExp) {
        if (name != null) {
            this.name = name;
        }
        if (category != null) {
            this.category = category;
        }
        if (proficiency != null) {
            this.proficiency = proficiency;
        }
        if (yearsOfExp != null) {
            this.yearsOfExp = yearsOfExp;
        }

        this.updatedAt = LocalDateTime.now();
    }
}
