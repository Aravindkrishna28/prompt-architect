package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.entity.PromptGenerationEntity;
import com.architect.promptarchitect.repository.PromptGenerationRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class HistoryController {

    private final PromptGenerationRepository repository;

    public HistoryController(PromptGenerationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/history")
    public List<Map<String, Object>> history() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(e -> Map.<String, Object>of(
                        "id", e.getId(),
                        "rawIdea", e.getRawIdea(),
                        "overallScore", e.getOverallScore() == null ? 0.0 : e.getOverallScore(),
                        "createdAt", e.getCreatedAt().toString()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> historyDetail(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> ResponseEntity.ok(e.getResultJson()))
                .orElse(ResponseEntity.notFound().build());
    }
}