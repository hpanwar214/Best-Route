

package com.lucidity.bestroute.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEvent {
    public static final String PICKUP = "PICKUP";
    public static final String DELIVERY = "DELIVERY";
    public static final String CREATED = "CREATED";
    String type;
    int index;
}