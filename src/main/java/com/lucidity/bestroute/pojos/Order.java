

package com.lucidity.bestroute.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    Integer id;
    Integer deliveryExecutiveId;
    Integer restaurantId;
    Integer customerId;
    Double processingTime;
}
