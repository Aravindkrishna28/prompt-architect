package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.model.PipelineResult;
import com.architect.promptarchitect.model.UserInput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class PromptArchitectController {

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        // Placeholder until Phase 3 wires in the real pipeline
        return new PipelineResult(input.idea(), null, null, null, null, null, null);
    }
}