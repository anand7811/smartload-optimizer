package com.smartload.optimizer.service;

import com.smartload.optimizer.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptimizationService {

    public OptimizeResponse optimize(OptimizeRequest request) {

        Truck truck = request.truck();
        List<Order> orders = request.orders();

        int n = orders.size();
        long bestPayout = 0;
        int bestMask = 0;

        for (int mask = 0; mask < (1 << n); mask++) {

            long totalPayout = 0;
            int totalWeight = 0;
            int totalVolume = 0;

            String routeOrigin = null;
            String routeDestination = null;
            boolean hasHazmat = false;
            boolean valid = true;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {

                    Order order = orders.get(i);

                    totalWeight += order.weight_lbs();
                    if (totalWeight > truck.max_weight_lbs()) {
                        valid = false;
                        break;
                    }

                    totalVolume += order.volume_cuft();
                    if (totalVolume > truck.max_volume_cuft()) {
                        valid = false;
                        break;
                    }

                    totalPayout += order.payout_cents();

                    // Route consistency
                    if (routeOrigin == null) {
                        routeOrigin = order.origin();
                        routeDestination = order.destination();
                    } else if (!routeOrigin.equals(order.origin()) ||
                            !routeDestination.equals(order.destination())) {
                        valid = false;
                        break;
                    }

                    // Hazmat isolation rule
                    if (order.is_hazmat()) {
                        if (hasHazmat) {
                            valid = false;
                            break;
                        }
                        hasHazmat = true;
                    }
                }
            }

            if (valid && totalPayout > bestPayout) {
                bestPayout = totalPayout;
                bestMask = mask;
            }
        }

        return buildResponse(bestMask, bestPayout, truck, orders);
    }

    private OptimizeResponse buildResponse(
            int mask,
            long payout,
            Truck truck,
            List<Order> orders
    ) {

        List<String> selectedIds = new ArrayList<>();
        int totalWeight = 0;
        int totalVolume = 0;

        for (int i = 0; i < orders.size(); i++) {
            if ((mask & (1 << i)) != 0) {
                Order o = orders.get(i);
                selectedIds.add(o.id());
                totalWeight += o.weight_lbs();
                totalVolume += o.volume_cuft();
            }
        }

        double weightUtil = (totalWeight * 100.0) / truck.max_weight_lbs();
        double volumeUtil = (totalVolume * 100.0) / truck.max_volume_cuft();

        return new OptimizeResponse(
                truck.id(),
                selectedIds,
                payout,
                totalWeight,
                totalVolume,
                Math.round(weightUtil * 100.0) / 100.0,
                Math.round(volumeUtil * 100.0) / 100.0
        );
    }
}
