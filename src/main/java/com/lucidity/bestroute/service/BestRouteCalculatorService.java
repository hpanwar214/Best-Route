

package com.lucidity.bestroute.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BestRouteCalculatorService {
    @Autowired
    List<DeliveryOptimizerService> deliveryOptimizerServices;

    public void computeRoute(){
        deliveryOptimizerServices.forEach(
                DeliveryOptimizerService::calculateDeliveryTime
        );
    }
}
