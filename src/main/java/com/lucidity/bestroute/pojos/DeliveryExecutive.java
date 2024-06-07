

package com.lucidity.bestroute.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryExecutive {
    Integer id;
    String name;
    Location currentLocation;
}
