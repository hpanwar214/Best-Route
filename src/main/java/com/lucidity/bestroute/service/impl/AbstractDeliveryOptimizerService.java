

package com.lucidity.bestroute.service.impl;

import com.lucidity.bestroute.data.RouteDataService;
import com.lucidity.bestroute.pojos.DeliveryEvent;
import com.lucidity.bestroute.pojos.Location;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.SequenceResult;
import com.lucidity.bestroute.service.DeliveryOptimizerService;
import com.lucidity.bestroute.util.HaversineCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class AbstractDeliveryOptimizerService implements DeliveryOptimizerService {
    @Autowired RouteDataService routeDataService;
    @Autowired HaversineCalculator haversineCalculator;

    public static final String LINE_BREAK = "\n";

    public void calculateDeliveryTime(){
        Map<Integer,List<Order>> orderMap = routeDataService.getOrders().stream().collect(Collectors.groupingBy(Order::getDeliveryExecutiveId));
        SequenceResult result;
        for(var orders: orderMap.entrySet()){
            var deliveryExecutive = routeDataService.getDeliveryExecutives().get(orders.getKey());
            var orderList = orders.getValue();
            long startTime = System.currentTimeMillis();
            result = findBestSequence(orderList, deliveryExecutive.getCurrentLocation());
            long endTime = System.currentTimeMillis();
            printSolution(result,getOptimizerName(),endTime-startTime);
        }
    }

    protected void printSolution(SequenceResult result,
                                 String strategy,
                                 Long timeElapsed) {
        StringBuilder answer = new StringBuilder();
        answer.append(LINE_BREAK).append(strategy).append(LINE_BREAK).append("---Best Sequence--").append(LINE_BREAK).append("Delivery");
        answer.append(LINE_BREAK);
        for(var event: result.getBestSequence()){
            if(event.getType().equals(DeliveryEvent.PICKUP)){
                answer.append("Order ").append(event.getIndex()).append(" ").append(event.getType()).append(" from ").append(routeDataService.getRestaurants().get(routeDataService.getOrders().get(event.getIndex()).getRestaurantId()).getName());
            }else {
                answer.append("Order ").append(event.getIndex()).append(" ").append(event.getType()).append(" to ").append(routeDataService.getCustomers().get(routeDataService.getOrders().get(event.getIndex()).getCustomerId()).getName());
            }
            answer.append(LINE_BREAK);
        }
        answer.append("-----------------").append(LINE_BREAK);
        answer.append("Total delivery time: ").append((int)(result.getBestTime() * 60)).append(" mins").append(LINE_BREAK);
        answer.append("Total calculation time: ").append(timeElapsed).append("ms");
        log.info(answer.toString());
    }
    abstract SequenceResult findBestSequence(List<Order> orders, Location initialLocation);
}
