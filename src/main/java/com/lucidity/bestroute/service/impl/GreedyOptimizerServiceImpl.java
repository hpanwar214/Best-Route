

package com.lucidity.bestroute.service.impl;

import com.lucidity.bestroute.pojos.DeliveryEvent;
import com.lucidity.bestroute.pojos.Location;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.SequenceResult;
import com.lucidity.bestroute.util.HaversineCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@org.springframework.core.annotation.Order(0)
@RequiredArgsConstructor
public class GreedyOptimizerServiceImpl extends AbstractDeliveryOptimizerService {

    @Override
    public String getOptimizerName() {
        return "Greedy Optimizer";
    }

    @Override
    public SequenceResult findBestSequence(List<Order> orders, Location initialLocation) {
        List<DeliveryEvent> events = new ArrayList<>();
        Location currentLocation = initialLocation;
        double currentTime = 0;
        String[] orderStatus = new String[orders.size()];
        Arrays.fill(orderStatus,DeliveryEvent.CREATED);

        while (events.size() < 2 * orders.size()) {
            DeliveryEvent nextEvent = null;
            double bestTime = Double.MAX_VALUE;

            for (int i = 0; i < orders.size(); i++) {
                if (orderStatus[i].equals(DeliveryEvent.CREATED)) {
                    double travelTime = haversineCalculator.calculate(
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            routeDataService.getRestaurants().get(orders.get(i).getRestaurantId()).getLocation().getLatitude(),
                            routeDataService.getRestaurants().get(orders.get(i).getRestaurantId()).getLocation().getLongitude());

                    double tempTravelTime = Math.max(currentTime+travelTime,orders.get(i).getProcessingTime());
                    if (tempTravelTime < bestTime) {
                        nextEvent = new DeliveryEvent(DeliveryEvent.PICKUP, i);
                        bestTime = tempTravelTime;
                    }
                } else if(orderStatus[i].equals(DeliveryEvent.PICKUP)) {
                    double travelTime = haversineCalculator.calculate(
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            routeDataService.getCustomers().get(orders.get(i).getCustomerId()).getLocation().getLatitude(),
                            routeDataService.getCustomers().get(orders.get(i).getCustomerId()).getLocation().getLongitude());

                    if (currentTime + travelTime < bestTime) {
                        nextEvent = new DeliveryEvent(DeliveryEvent.DELIVERY, i);
                        bestTime = currentTime + travelTime;
                    }
                }
            }

            if (nextEvent != null) {
                events.add(nextEvent);
                if (nextEvent.getType().equals(DeliveryEvent.PICKUP)) {
                    orderStatus[nextEvent.getIndex()] = DeliveryEvent.PICKUP;
                    currentTime = Math.max(currentTime +
                                    haversineCalculator.calculate(
                                            currentLocation.getLatitude(),
                                            currentLocation.getLongitude(),
                                            routeDataService.getRestaurants().get(orders.get(nextEvent.getIndex()).getRestaurantId()).getLocation().getLatitude(),
                                            routeDataService.getRestaurants().get(orders.get(nextEvent.getIndex()).getRestaurantId()).getLocation().getLongitude()),
                            orders.get(nextEvent.getIndex()).getProcessingTime());
                    currentLocation = routeDataService.getRestaurants().get(orders.get(nextEvent.getIndex()).getRestaurantId()).getLocation();
                } else {
                    orderStatus[nextEvent.getIndex()] = DeliveryEvent.DELIVERY;
                    currentTime += haversineCalculator.calculate(
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            routeDataService.getCustomers().get(orders.get(nextEvent.getIndex()).getCustomerId()).getLocation().getLatitude(),
                            routeDataService.getCustomers().get(orders.get(nextEvent.getIndex()).getCustomerId()).getLocation().getLongitude());
                    currentLocation = routeDataService.getCustomers().get(orders.get(nextEvent.getIndex()).getCustomerId()).getLocation();
                }
            } else {
                // If no valid next event is found, break out of the loop to prevent infinite loop
                log.error(getOptimizerName()+ ": No valid next event found. Breaking out of loop.");
                break;
            }
        }

        return new SequenceResult(events, currentTime);
    }
}
