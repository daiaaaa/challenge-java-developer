package br.com.neurotech.challenge.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequest(

        @NotBlank(message = "Name is required and cannot be blank")
        String name,

        @NotNull(message = "Age is required")
        @Min(value = 18, message = "Minimum allowed age is 18")
        Integer age,

        @NotNull(message = "Income is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Income must be greater than zero")
        Double income

) {}


