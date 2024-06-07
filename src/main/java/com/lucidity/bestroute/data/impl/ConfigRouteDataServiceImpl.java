

package com.lucidity.bestroute.data.impl;

import com.lucidity.bestroute.data.RouteDataService;
import com.lucidity.bestroute.pojos.Customer;
import com.lucidity.bestroute.pojos.DeliveryExecutive;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.Restaurant;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@ConfigurationProperties(prefix = "data")
@ConditionalOnProperty(name = "data.mode", havingValue = "config")
public class ConfigRouteDataServiceImpl implements RouteDataService {
    private List<Customer> customers;
    private List<Restaurant> restaurants;
    private List<Order> orders;
    private List<DeliveryExecutive> deliveryExecutives;
}
