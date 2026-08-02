package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.agent.ai.PromptArchitectAssistant;
import com.architect.promptarchitect.model.*;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PromptArchitectAgent {

    private final PromptArchitectAssistant assistant;

    public PromptArchitectAgent(ChatModel chatModel) {
        this.assistant = AiServices.create(PromptArchitectAssistant.class, chatModel);
    }

    public ArchitectedPrompt run(IntentAnalysis intent, TaskClassification classification, PromptStrategy strategy) {
        String constraintsCsv = String.join(", ", intent.constraints());
        String supportingCsv = strategy.supportingTechniques().stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        return assistant.architect(
                intent.primaryGoal(),
                classification.domain().name(),
                classification.subtype(),
                constraintsCsv.isBlank() ? "none specified" : constraintsCsv,
                strategy.primaryTechnique().name(),
                supportingCsv.isBlank() ? "none" : supportingCsv
        );
    }

    public ArchitectedPrompt refine(ArchitectedPrompt previous, String feedback) {
        return assistant.refine(previous.fullAssembledPrompt(), feedback);
    }
}