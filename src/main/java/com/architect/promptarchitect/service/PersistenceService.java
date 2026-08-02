package com.architect.promptarchitect.service;

import com.architect.promptarchitect.entity.PromptGenerationEntity;
import com.architect.promptarchitect.model.PipelineResult;
import com.architect.promptarchitect.repository.PromptGenerationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private final PromptGenerationRepository repository;
    private final ObjectMapper objectMapper;

    public PersistenceService(PromptGenerationRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public void save(PipelineResult result) {
        try {
            String json = objectMapper.writeValueAsString(result);
            Double score = null;
            if (result.evaluation() != null) {
                score = ((com.architect.promptarchitect.model.EvaluationScore) result.evaluation()).overallScore();
            }
            PromptGenerationEntity entity = new PromptGenerationEntity(result.rawIdea(), score, json);
            repository.save(entity);
        } catch (Exception e) {
            // Persistence failure should never break the actual pipeline response
            System.err.println("Failed to persist generation: " + e.getMessage());
        }
    }
}