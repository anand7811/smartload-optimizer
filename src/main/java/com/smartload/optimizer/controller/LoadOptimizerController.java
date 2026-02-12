package com.smartload.optimizer.controller;

import com.smartload.optimizer.model.*;
import com.smartload.optimizer.service.OptimizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    private final OptimizationService service;

    public LoadOptimizerController(OptimizationService service) {
        this.service = service;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizeResponse> optimize(
            @Valid @RequestBody OptimizeRequest request
    ) {
        return ResponseEntity.ok(service.optimize(request));
    }
}
