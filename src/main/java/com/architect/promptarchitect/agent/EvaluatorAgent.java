package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.agent.ai.EvaluatorAssistant;
import com.architect.promptarchitect.model.ArchitectedPrompt;
import com.architect.promptarchitect.model.EvaluationScore;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class EvaluatorAgent {

    private final EvaluatorAssistant assistant;

    public EvaluatorAgent(ChatModel chatModel) {
        this.assistant = AiServices.create(EvaluatorAssistant.class, chatModel);
    }

    public EvaluationScore run(ArchitectedPrompt prompt) {
        return assistant.evaluate(prompt.fullAssembledPrompt());
    }
}