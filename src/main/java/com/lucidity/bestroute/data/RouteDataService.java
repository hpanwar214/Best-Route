

package com.lucidity.bestroute.data;

import com.lucidity.bestroute.pojos.Customer;
import com.lucidity.bestroute.pojos.DeliveryExecutive;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.Restaurant;

import java.util.List;

public interface RouteDataService {
    List<Customer> getCustomers();
    List<Restaurant> getRestaurants();
    List<Order> getOrders();
    List<DeliveryExecutive> getDeliveryExecutives();
}
