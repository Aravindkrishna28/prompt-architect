package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.model.*;
import com.architect.promptarchitect.service.PersistenceService;
import org.springframework.stereotype.Service;

@Service
public class PromptPipelineOrchestrator {

    private static final int MAX_REFINEMENT_ATTEMPTS = 3;

    private final IntentAnalyzerAgent intentAnalyzerAgent;
    private final TaskClassifierAgent taskClassifierAgent;
    private final PromptStrategySelector strategySelector;
    private final PromptArchitectAgent promptArchitectAgent;
    private final EvaluatorAgent evaluatorAgent;
    private final PersistenceService persistenceService;

    public PromptPipelineOrchestrator(IntentAnalyzerAgent intentAnalyzerAgent,
                                      TaskClassifierAgent taskClassifierAgent,
                                      PromptStrategySelector strategySelector,
                                      PromptArchitectAgent promptArchitectAgent,
                                      EvaluatorAgent evaluatorAgent,
                                      PersistenceService persistenceService) {
        this.intentAnalyzerAgent = intentAnalyzerAgent;
        this.taskClassifierAgent = taskClassifierAgent;
        this.strategySelector = strategySelector;
        this.promptArchitectAgent = promptArchitectAgent;
        this.evaluatorAgent = evaluatorAgent;
        this.persistenceService = persistenceService;
    }

    public PipelineResult run(String rawIdea) {
        IntentAnalysis intent = intentAnalyzerAgent.run(rawIdea);
        TaskClassification classification = taskClassifierAgent.run(rawIdea, intent);
        PromptStrategy strategy = strategySelector.select(classification, intent);

        ArchitectedPrompt prompt = promptArchitectAgent.run(intent, classification, strategy);
        EvaluationScore score = evaluatorAgent.run(prompt);

        ArchitectedPrompt finalPrompt = prompt;
        EvaluationScore finalScore = score;
        int attempts = 0;

        while (finalScore.needsRefinement() && attempts < MAX_REFINEMENT_ATTEMPTS) {
            finalPrompt = promptArchitectAgent.refine(finalPrompt, finalScore.feedback());
            finalScore = evaluatorAgent.run(finalPrompt);
            attempts++;
        }

        PipelineResult result = new PipelineResult(
                rawIdea, intent, classification, strategy, finalPrompt, finalScore,
                attempts > 0 ? "refined after " + attempts + " attempt(s)" : "no refinement needed"
        );

        persistenceService.save(result);

        return result;
    }
}