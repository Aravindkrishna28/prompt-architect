package com.architect.promptarchitect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prompt_generations")
public class PromptGenerationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String rawIdea;

    @Column(nullable = false)
    private Double overallScore;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Lob
    @Column(nullable = false)
    private String resultJson;

    protected PromptGenerationEntity() {
        // required by JPA
    }

    public PromptGenerationEntity(String rawIdea, Double overallScore, String resultJson) {
        this.rawIdea = rawIdea;
        this.overallScore = overallScore;
        this.resultJson = resultJson;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getRawIdea() { return rawIdea; }
    public Double getOverallScore() { return overallScore; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getResultJson() { return resultJson; }
}