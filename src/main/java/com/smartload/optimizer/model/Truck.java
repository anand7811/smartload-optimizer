package com.smartload.optimizer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record Truck(
        @NotBlank String id,
        @Positive int max_weight_lbs,
        @Positive int max_volume_cuft
) {}
