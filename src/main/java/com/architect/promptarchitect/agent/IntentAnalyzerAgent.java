package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.agent.ai.IntentAnalyzerAssistant;
import com.architect.promptarchitect.model.IntentAnalysis;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class IntentAnalyzerAgent {

    private final IntentAnalyzerAssistant assistant;

    public IntentAnalyzerAgent(ChatModel chatModel) {
        this.assistant = AiServices.create(IntentAnalyzerAssistant.class, chatModel);
    }

    public IntentAnalysis run(String rawIdea) {
        if (rawIdea == null || rawIdea.isBlank()) {
            throw new IllegalArgumentException("rawIdea must not be blank");
        }
        return RetryHelper.withRetry("IntentAnalyzer", () -> assistant.analyze(rawIdea));
    }
}