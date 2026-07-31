package com.architect.promptarchitect.agent.ai;

import com.architect.promptarchitect.model.TaskClassification;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface TaskClassifierAssistant {

    @SystemMessage("""
            You are a Task Classifier. Classify the user's idea into exactly one domain:
            CODING, WRITING, ANALYSIS, UI_UX, or OTHER.
            Also provide a short subtype (2-4 words, e.g. "REST API design", "blog post", "data visualization")
            and one sentence of reasoning for the classification.
            """)
    @UserMessage("""
            Idea: {{idea}}
            Extracted goal: {{goal}}
            Domain hint: {{domainHint}}
            """)
    TaskClassification classify(@V("idea") String idea,
                                 @V("goal") String goal,
                                 @V("domainHint") String domainHint);
}