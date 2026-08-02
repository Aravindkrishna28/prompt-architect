package com.architect.promptarchitect.agent;

import dev.langchain4j.service.output.OutputParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class RetryHelper {

    private static final Logger log = LoggerFactory.getLogger(RetryHelper.class);
    private static final int MAX_ATTEMPTS = 3;

    private RetryHelper() {}

    public static <T> T withRetry(String stepName, Supplier<T> call) {
        RuntimeException lastFailure = null;
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                return call.get();
            } catch (OutputParsingException e) {
                lastFailure = e;
                log.warn("{}: attempt {}/{} failed to parse model output, retrying...",
                        stepName, attempt, MAX_ATTEMPTS);
            }
        }
        throw new IllegalStateException(
                stepName + ": failed to get valid structured output after " + MAX_ATTEMPTS + " attempts",
                lastFailure
        );
    }
}