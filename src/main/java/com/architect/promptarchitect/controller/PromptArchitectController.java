package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.agent.IntentAnalyzerAgent;
import com.architect.promptarchitect.model.IntentAnalysis;
import com.architect.promptarchitect.model.PipelineResult;
import com.architect.promptarchitect.model.UserInput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class PromptArchitectController {

    private final IntentAnalyzerAgent intentAnalyzerAgent;

    public PromptArchitectController(IntentAnalyzerAgent intentAnalyzerAgent) {
        this.intentAnalyzerAgent = intentAnalyzerAgent;
    }

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        IntentAnalysis intent = intentAnalyzerAgent.run(input.idea());
        return new PipelineResult(input.idea(), intent, null, null, null, null, null);
    }
}