

package com.lucidity.bestroute.service.impl;

import com.lucidity.bestroute.pojos.DeliveryEvent;
import com.lucidity.bestroute.pojos.Location;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.SequenceResult;
import com.lucidity.bestroute.util.HaversineCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@org.springframework.core.annotation.Order(1)
@Service
@RequiredArgsConstructor
public class BruteForceOptimizerServiceImpl extends AbstractDeliveryOptimizerService {

    @Override
    public String getOptimizerName() {
        return "Brute Force Optimizer";
    }

    public SequenceResult findBestSequence(List<Order> orders, Location initialLocation) {
        int n = orders.size();
        List<DeliveryEvent> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            events.add(new DeliveryEvent(DeliveryEvent.PICKUP, i));
            events.add(new DeliveryEvent(DeliveryEvent.DELIVERY, i));
        }

        List<List<DeliveryEvent>> permutations = generatePermutations(events);
        double bestTime = Double.MAX_VALUE;
        List<DeliveryEvent> bestSequence = null;

        for (List<DeliveryEvent> perm : permutations) {
            if (isValidSequence(perm, n)) {
                double totalTime = calculateTotalTime(perm, orders, initialLocation);
                if (totalTime < bestTime) {
                    bestTime = totalTime;
                    bestSequence = perm;
                }
            }
        }
        return new SequenceResult(bestSequence,bestTime);
    }
    public double calculateTotalTime(List<DeliveryEvent> sequence, List<Order> orders, Location initialLocation) {
        double currentTime = 0;
        Location currentLocation = initialLocation;

        for (DeliveryEvent event : sequence) {
            Order order = orders.get(event.getIndex());
            if (event.getType().equals(DeliveryEvent.PICKUP)) {
                double travelTime = haversineCalculator.calculate(currentLocation.getLatitude(), currentLocation.getLongitude(), routeDataService.getRestaurants().get(order.getRestaurantId()).getLocation().getLatitude(), routeDataService.getRestaurants().get(order.getRestaurantId()).getLocation().getLongitude());
                currentTime += travelTime;
                currentTime = Math.max(currentTime, routeDataService.getOrders().get(order.getId()).getProcessingTime()); // Wait for preparation time if needed
                currentLocation = routeDataService.getRestaurants().get(order.getRestaurantId()).getLocation();
            } else if (event.getType().equals(DeliveryEvent.DELIVERY)) {
                double travelTime = haversineCalculator.calculate(currentLocation.getLatitude(), currentLocation.getLongitude(), routeDataService.getCustomers().get(order.getCustomerId()).getLocation().getLatitude(), routeDataService.getCustomers().get(order.getCustomerId()).getLocation().getLongitude());
                currentTime += travelTime;
                currentLocation = routeDataService.getCustomers().get(order.getCustomerId()).getLocation();
            }
        }

        return currentTime;
    }
    private static boolean isValidSequence(List<DeliveryEvent> sequence, int n) {
        int[] pickups = new int[n];
        int[] deliveries = new int[n];
        Arrays.fill(pickups, -1);
        Arrays.fill(deliveries, -1);

        for (int i = 0; i < sequence.size(); i++) {
            DeliveryEvent event = sequence.get(i);
            if (event.getType().equals(DeliveryEvent.PICKUP)) {
                pickups[event.getIndex()] = i;
            } else if (event.getType().equals(DeliveryEvent.DELIVERY)) {
                deliveries[event.getIndex()] = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (pickups[i] == -1 || deliveries[i] == -1 || pickups[i] > deliveries[i]) {
                return false;
            }
        }

        return true;
    }
    private static List<List<DeliveryEvent>> generatePermutations(List<DeliveryEvent> events) {
        List<List<DeliveryEvent>> result = new ArrayList<>();
        permute(events, 0, result);
        return result;
    }

    private static void permute(List<DeliveryEvent> events, int start, List<List<DeliveryEvent>> result) {
        if (start == events.size() - 1) {
            result.add(new ArrayList<>(events));
            return;
        }

        for (int i = start; i < events.size(); i++) {
            java.util.Collections.swap(events, start, i);
            permute(events, start + 1, result);
            java.util.Collections.swap(events, start, i);
        }
    }
}
