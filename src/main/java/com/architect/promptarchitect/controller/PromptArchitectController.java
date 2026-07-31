package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.agent.IntentAnalyzerAgent;
import com.architect.promptarchitect.agent.TaskClassifierAgent;
import com.architect.promptarchitect.model.IntentAnalysis;
import com.architect.promptarchitect.model.PipelineResult;
import com.architect.promptarchitect.model.TaskClassification;
import com.architect.promptarchitect.model.UserInput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class PromptArchitectController {

    private final IntentAnalyzerAgent intentAnalyzerAgent;
    private final TaskClassifierAgent taskClassifierAgent;

    public PromptArchitectController(IntentAnalyzerAgent intentAnalyzerAgent,
                                     TaskClassifierAgent taskClassifierAgent) {
        this.intentAnalyzerAgent = intentAnalyzerAgent;
        this.taskClassifierAgent = taskClassifierAgent;
    }

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        IntentAnalysis intent = intentAnalyzerAgent.run(input.idea());
        TaskClassification classification = taskClassifierAgent.run(input.idea(), intent);
        return new PipelineResult(input.idea(), intent, classification, null, null, null, null);
    }
}