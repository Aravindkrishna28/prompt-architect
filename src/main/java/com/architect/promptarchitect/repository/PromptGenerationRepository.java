package com.architect.promptarchitect.repository;

import com.architect.promptarchitect.entity.PromptGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PromptGenerationRepository extends JpaRepository<PromptGenerationEntity, Long> {
    List<PromptGenerationEntity> findAllByOrderByCreatedAtDesc();
}