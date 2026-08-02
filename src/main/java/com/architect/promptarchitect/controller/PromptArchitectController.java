package com.architect.promptarchitect.controller;

import com.architect.promptarchitect.agent.*;
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
    private final EvaluatorAgent evaluatorAgent;

    public PromptArchitectController(IntentAnalyzerAgent intentAnalyzerAgent,
                                     TaskClassifierAgent taskClassifierAgent,
                                     PromptStrategySelector strategySelector,
                                     PromptArchitectAgent promptArchitectAgent,
                                     EvaluatorAgent evaluatorAgent) {
        this.intentAnalyzerAgent = intentAnalyzerAgent;
        this.taskClassifierAgent = taskClassifierAgent;
        this.strategySelector = strategySelector;
        this.promptArchitectAgent = promptArchitectAgent;
        this.evaluatorAgent = evaluatorAgent;
    }

    @PostMapping("/analyze")
    public PipelineResult analyze(@Valid @RequestBody UserInput input) {
        IntentAnalysis intent = intentAnalyzerAgent.run(input.idea());
        TaskClassification classification = taskClassifierAgent.run(input.idea(), intent);
        PromptStrategy strategy = strategySelector.select(classification, intent);
        ArchitectedPrompt architectedPrompt = promptArchitectAgent.run(intent, classification, strategy);
        EvaluationScore evaluation = evaluatorAgent.run(architectedPrompt);
        return new PipelineResult(input.idea(), intent, classification, strategy, architectedPrompt, evaluation, null);
    }
}