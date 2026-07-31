package com.architect.promptarchitect.agent;

import com.architect.promptarchitect.agent.ai.TaskClassifierAssistant;
import com.architect.promptarchitect.model.IntentAnalysis;
import com.architect.promptarchitect.model.TaskClassification;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class TaskClassifierAgent {

    private final TaskClassifierAssistant assistant;

    public TaskClassifierAgent(ChatModel chatModel) {
        this.assistant = AiServices.create(TaskClassifierAssistant.class, chatModel);
    }

    public TaskClassification run(String rawIdea, IntentAnalysis intent) {
        return assistant.classify(rawIdea, intent.primaryGoal(), intent.domainHint());
    }
}