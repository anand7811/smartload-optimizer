package com.smartload.optimizer.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OptimizeRequest(
        @NotNull @Valid Truck truck,
        @NotNull @Valid List<Order> orders
) {}
