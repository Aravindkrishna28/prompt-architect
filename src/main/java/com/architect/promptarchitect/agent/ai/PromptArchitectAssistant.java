package com.architect.promptarchitect.agent.ai;

import com.architect.promptarchitect.model.ArchitectedPrompt;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface PromptArchitectAssistant {

    @SystemMessage("""
            You are a Prompt Architect. You design production-quality prompts for other LLMs.
            Given the task details and a required prompting technique, produce:
            - systemPersona: a concise persona/role definition for the target LLM.
            - taskDescription: a precise task description incorporating the technique (e.g. if CHAIN_OF_THOUGHT, explicitly instruct step-by-step reasoning).
            - constraints: the constraint list, cleaned up and de-duplicated.
            - outputFormat: a clear spec of the expected output structure.
            - fullAssembledPrompt: the persona, task description, constraints, and output format combined into one ready-to-use prompt text.
            CRITICAL FORMATTING RULE: every field must be valid JSON string content. Do NOT use literal line
            breaks inside any string value, even when listing multiple steps or points — write everything as a
            single continuous line of text, using semicolons or numbered phrases separated by spaces (e.g.
            "1. Do X; 2. Do Y; 3. Do Z") instead of putting each item on its own line.
            """)
    @UserMessage("""
            Primary goal: {{goal}}
            Domain: {{domain}} ({{subtype}})
            Constraints: {{constraints}}
            Required technique: {{technique}}
            Supporting techniques: {{supportingTechniques}}
            """)
    ArchitectedPrompt architect(@V("goal") String goal,
                                @V("domain") String domain,
                                @V("subtype") String subtype,
                                @V("constraints") String constraints,
                                @V("technique") String technique,
                                @V("supportingTechniques") String supportingTechniques);

    @SystemMessage("""
            You are a Prompt Architect refining an existing prompt based on evaluator feedback.
            Keep everything that already works well; fix only what the feedback calls out.
            Return the same structured fields as before (systemPersona, taskDescription, constraints,
            outputFormat, fullAssembledPrompt) for the IMPROVED prompt.
            CRITICAL FORMATTING RULE: every field must be valid JSON string content. Do NOT use literal line
            breaks inside any string value, even when listing multiple steps or points — write everything as a
            single continuous line of text, using semicolons or numbered phrases separated by spaces (e.g.
            "1. Do X; 2. Do Y; 3. Do Z") instead of putting each item on its own line.
            """)
    @UserMessage("""
            Original prompt:
            {{originalPrompt}}

            Evaluator feedback:
            {{feedback}}
            """)
    ArchitectedPrompt refine(@V("originalPrompt") String originalPrompt,
                             @V("feedback") String feedback);
}