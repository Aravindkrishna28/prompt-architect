package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptStrategySelector {

    public PromptStrategy select(TaskClassification classification, IntentAnalysis intent) {
        TaskDomain domain = classification.domain();
        String complexity = intent.complexity().toUpperCase();

        return switch (domain) {
            case CODING -> selectForCoding(complexity);
            case WRITING -> selectForWriting(complexity);
            case ANALYSIS -> selectForAnalysis(complexity);
            case UI_UX -> selectForUiUx(complexity);
            case OTHER -> new PromptStrategy(
                    PromptTechnique.ZERO_SHOT, List.of(),
                    "No strong domain signal; default to zero-shot with clear instructions.");
        };
    }

    private PromptStrategy selectForCoding(String complexity) {
        if (complexity.equals("HIGH")) {
            return new PromptStrategy(
                    PromptTechnique.REACT,
                    List.of(PromptTechnique.CHAIN_OF_THOUGHT, PromptTechnique.ROLE_BASED),
                    "High-complexity coding tasks benefit from reasoning+action loops with an expert persona.");
        }
        return new PromptStrategy(
                PromptTechnique.CHAIN_OF_THOUGHT,
                List.of(PromptTechnique.ROLE_BASED),
                "Coding tasks benefit from step-by-step reasoning framed by an expert persona.");
    }

    private PromptStrategy selectForWriting(String complexity) {
        return new PromptStrategy(
                PromptTechnique.FEW_SHOT,
                List.of(PromptTechnique.ROLE_BASED),
                "Writing tasks benefit from style-anchoring examples plus a persona for tone consistency.");
    }

    private PromptStrategy selectForAnalysis(String complexity) {
        if (complexity.equals("HIGH")) {
            return new PromptStrategy(
                    PromptTechnique.TREE_OF_THOUGHT,
                    List.of(PromptTechnique.CHAIN_OF_THOUGHT),
                    "Complex analysis benefits from exploring multiple reasoning branches before concluding.");
        }
        return new PromptStrategy(
                PromptTechnique.CHAIN_OF_THOUGHT,
                List.of(),
                "Analytical tasks need explicit step-by-step reasoning.");
    }

    private PromptStrategy selectForUiUx(String complexity) {
        return new PromptStrategy(
                PromptTechnique.ROLE_BASED,
                List.of(PromptTechnique.FEW_SHOT),
                "UI/UX tasks benefit from a design-persona framing plus concrete style examples.");
    }
}