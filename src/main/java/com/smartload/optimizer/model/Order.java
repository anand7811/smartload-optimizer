package com.smartload.optimizer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record Order(
        @NotBlank String id,
        @Positive long payout_cents,
        @Positive int weight_lbs,
        @Positive int volume_cuft,
        @NotBlank String origin,
        @NotBlank String destination,
        LocalDate pickup_date,
        LocalDate delivery_date,
        boolean is_hazmat
) {}
