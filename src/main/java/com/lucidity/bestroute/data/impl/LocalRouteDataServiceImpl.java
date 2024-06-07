

package com.lucidity.bestroute.data.impl;

import com.lucidity.bestroute.data.RouteDataService;
import com.lucidity.bestroute.pojos.Customer;
import com.lucidity.bestroute.pojos.DeliveryExecutive;
import com.lucidity.bestroute.pojos.Location;
import com.lucidity.bestroute.pojos.Order;
import com.lucidity.bestroute.pojos.Restaurant;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@ConditionalOnProperty(name = "data.mode", havingValue = "local", matchIfMissing = true)
public class LocalRouteDataServiceImpl implements RouteDataService {
    private List<Customer> customers;
    private List<Restaurant> restaurants;
    private List<Order> orders;
    private List<DeliveryExecutive> deliveryExecutives;

    public LocalRouteDataServiceImpl(){
        customers = createCustomers();
        restaurants = createRestaurants();
        orders = createOrders();
        deliveryExecutives = createDeliveryExecutives();
    }
    List<Customer> createCustomers(){
        List<Customer> temp = new ArrayList<>();
        temp.add(new Customer(0,"C1",new Location(12.9250,77.5938)));
        temp.add(new Customer(1,"C2",new Location(12.9279, 77.6271)));
        temp.add(new Customer(2,"C3",new Location(12.9300,77.6200)));
        temp.add(new Customer(3,"C4",new Location(12.9320, 77.6100)));

        return temp;
    }
    List<Restaurant> createRestaurants(){
        List<Restaurant> temp = new ArrayList<>();
        temp.add(new Restaurant(0,"R1",new Location(12.9337, 77.6101)));
        temp.add(new Restaurant(1,"R2",new Location(12.9278, 77.6270)));
        temp.add(new Restaurant(2,"R3",new Location(12.9400, 77.6200)));
        temp.add(new Restaurant(3,"R4",new Location( 12.9380, 77.6050)));
        return temp;
    }
    List<Order> createOrders(){
        List<Order> temp = new ArrayList<>();
        temp.add(new Order(0,0,0,0, 0.5));
        temp.add(new Order(1,0,1,1, 0.3));
        temp.add(new Order(2,0,2,2, 0.4));
        temp.add(new Order(3,0,3,3, 0.6));

        temp.add(new Order(4,1,3,1, 0.6));
        temp.add(new Order(5,1,2,2, 0.5));
        temp.add(new Order(6,1,0,3, 1.0));
        temp.add(new Order(7,1,1,0, 0.4));
        temp.add(new Order(8,1,2,3, 1.6));
        temp.add(new Order(9,1,2,1, 0.7));
        return temp;
    }
    List<DeliveryExecutive> createDeliveryExecutives(){
        List<DeliveryExecutive> temp = new ArrayList<>();
        temp.add( new DeliveryExecutive(0,"Aman", new Location( 12.9352, 77.6245)));
        temp.add( new DeliveryExecutive(1,"Baman ", new Location( 12.9452, 77.6035)));
        return temp;
    }
}
