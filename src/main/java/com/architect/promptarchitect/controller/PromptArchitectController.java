package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.agent.IntentAnalyzerAgent;
import com.architect.promptarchitect.agent.PromptArchitectAgent;
import com.architect.promptarchitect.agent.PromptStrategySelector;
import com.architect.promptarchitect.agent.TaskClassifierAgent;
import com.architect.promptarchitect.model.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prompt-architect")
public class PromptArchitectController {

    private final IntentAnalyzerAgent intentAnalyzerAgent;
    private final TaskClassifierAgent taskClassifierAgent;
    private final PromptStrategySelector strategySelector;
    private final PromptArchitectAgent promptArchitectAgent;

    public PromptArchitectController(IntentAnalyzerAgent intentAnalyzerAgent,
                                     TaskClassifierAgent taskClassifierAgent,
                                     PromptStrategySelector strategySelector,
                                     PromptArchitectAgent promptArchitectAgent) {
        this.intentAnalyzerAgent = intentAnalyzerAgent;
        this.taskClassifierAgent = taskClassifierAgent;
        this.strategySelector = strategySelector;
        this.promptArchitectAgent = promptArchitectAgent;
    }

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        IntentAnalysis intent = intentAnalyzerAgent.run(input.idea());
        TaskClassification classification = taskClassifierAgent.run(input.idea(), intent);
        PromptStrategy strategy = strategySelector.select(classification, intent);
        ArchitectedPrompt architectedPrompt = promptArchitectAgent.run(intent, classification, strategy);
        return new PipelineResult(input.idea(), intent, classification, strategy, architectedPrompt, null, null);
    }
}