package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.agent.PromptPipelineOrchestrator;
import com.architect.promptarchitect.model.PipelineResult;
import com.architect.promptarchitect.model.UserInput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class PromptArchitectController {

    private final PromptPipelineOrchestrator orchestrator;

    public PromptArchitectController(PromptPipelineOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        return orchestrator.run(input.idea());
    }
}