package com.architect.promptarchitect.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInput(
        @NotBlank(message = "idea must not be blank")
        @Size(min = 5, max = 2000, message = "idea must be between 5 and 2000 characters")
        String idea
) {}