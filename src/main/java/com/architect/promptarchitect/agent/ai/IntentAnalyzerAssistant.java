package com.architect.promptarchitect.agent.ai;

import com.architect.promptarchitect.model.IntentAnalysis;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface IntentAnalyzerAssistant {

    @SystemMessage("""
            You are an Intent Analyzer for a prompt-engineering pipeline.
            Given a crude, informal user idea, extract:
            - primaryGoal: one clear sentence describing what the user wants.
            - constraints: a list of explicit or clearly implied constraints (tech stack, tone, length, audience, etc). Empty list if none.
            - complexity: LOW, MEDIUM, or HIGH based on scope and ambiguity.
            - domainHint: a short free-text guess at the domain (e.g. "web app", "creative writing", "data analysis").
            Respond ONLY with the structured fields requested — no extra commentary.
            """)
    @UserMessage("User idea: {{idea}}")
    IntentAnalysis analyze(String idea);
}