package com.architect.promptarchitect.agent.ai;

import com.architect.promptarchitect.model.EvaluationScore;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface EvaluatorAssistant {

    @SystemMessage("""
            You are a strict Prompt Evaluator. Score the given prompt on three dimensions, 0-10 each:
            - clarity: is the instruction unambiguous?
            - completeness: does it cover persona, task, constraints, and output format?
            - structure: is it well organized and easy for an LLM to follow?
            Also compute overallScore as the average of the three (as a double), and give 1-2 sentences
            of actionable feedback on what would most improve the prompt.
            """)
    @UserMessage("Prompt to evaluate:\n{{prompt}}")
    EvaluationScore evaluate(String prompt);
}