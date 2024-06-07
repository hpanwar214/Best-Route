# Best-Route
Find best route  finder solution which has least delivery time

# Delivery Optimization Service

This Spring Boot application calculates the optimal sequence for picking up and delivering orders to minimize the total delivery time. The application uses two algorithms for this purpose:

1. **Permutation and Combination Algorithm**: Evaluates all possible sequences of events to find the optimal route.
2. **Greedy Algorithm**: Uses a heuristic approach to find a near-optimal route in a shorter time.

## Algorithms

### Permutation and Combination Algorithm

#### Overview
The Permutation and Combination algorithm evaluates all possible sequences of pickup and delivery events to find the optimal route that minimizes the total delivery time. This exhaustive search method ensures that the absolute best sequence is identified.

#### How It Works
1. **Generate Events**: For each order, generate two events - one for pickup and one for delivery.
2. **Generate Permutations**: Create all possible permutations of these events.
3. **Validate Sequences**: Filter the permutations to ensure that each delivery event occurs after its corresponding pickup event.
4. **Calculate Total Time**: For each valid sequence, calculate the total delivery time using the Haversine formula to determine travel times between locations.
5. **Select Optimal Sequence**: Choose the sequence with the shortest total delivery time.

#### Advantages
- **Optimal Solution**: This algorithm guarantees finding the optimal sequence.
- **Comprehensive**: Evaluates all possible sequences, ensuring no potential solution is overlooked.

#### Disadvantages
- **Computationally Intensive**: The number of permutations grows factorially with the number of orders, making this method computationally expensive and impractical for a large number of orders.

### Greedy Algorithm

#### Overview
The Greedy algorithm uses a heuristic approach to find a near-optimal route quickly. It makes locally optimal choices at each step with the goal of finding a globally optimal solution. While it may not always find the absolute best sequence, it provides a good solution in a significantly shorter time.

#### How It Works
1. **Initialize**: Start at the delivery executive's initial location.
2. **Iterative Selection**: At each step, choose the next event (pickup or delivery) based on a heuristic, such as the shortest travel time or the earliest preparation completion.
3. **Update State**: Update the current location and time after each event.
4. **Repeat**: Continue the process until all pickups and deliveries are completed.

#### Advantages
- **Efficiency**: Much faster than the Permutation and Combination algorithm, especially as the number of orders increases.
- **Scalability**: Suitable for handling a large number of orders without significant computational overhead.

#### Disadvantages
- **Suboptimal Solution**: May not always find the optimal sequence due to its heuristic nature.
- **Local Optima**: The algorithm might get stuck in local optima and miss the global optimum.

### Example Output

Given a set of orders with specified consumer and restaurant locations, and preparation times, the application calculates the best route and total delivery time using both algorithms. The results are compared to highlight the trade-offs between optimality and computational efficiency.

```plaintext
Greedy Optimizer
---Best Sequence--
Delivery
Order 3 PICKUP from R4
Order 1 PICKUP from R2
Order 1 DELIVERY to C2
Order 0 PICKUP from R1
Order 3 DELIVERY to C4
Order 0 DELIVERY to C1
Order 2 PICKUP from R3
Order 2 DELIVERY to C3
-----------------
Total delivery time: 61 mins
Total calculation time: 0ms

Brute Force Optimizer
---Best Sequence--
Delivery
Order 3 PICKUP from R4
Order 1 PICKUP from R2
Order 0 PICKUP from R1
Order 3 DELIVERY to C4
Order 0 DELIVERY to C1
Order 1 DELIVERY to C2
Order 2 PICKUP from R3
Order 2 DELIVERY to C3
-----------------
Total delivery time: 60 mins
Total calculation time: 9ms

```

## Installation

1. **Clone the Repository**
    ```bash
    git clone https://github.com/hpanwar214/Best-Route
    cd bestroute
    ```

2. **Build the Project**
    ```bash
    ./gradlew build
    ```

3. **Run the Application**
    ```bash
    ./gradlew bootRun
    ```
4. **Run the Application from IntelliJ**

   Open the repo in intellij, let the build get automatically trigger from intelliJ. Got to BestRouteApplication file and run the main method.

   
### About inputs

Inputs to the algorithm are taken from RouteDataService interface, it has two implementation one Config based and Java based.
1. **Config Based (ConfigRouteDataServiceImpl.java)** : Takes input from application.yml, when mode in YML file is set to config, inputs will be picked from there

2. **Java File Based (LocalRouteDataServiceImpl.java)**: Takes inputs from local java file, this file contains dummy objects, which contains valid set of data.

## Configuration

- **Setting speed of delivery executive**: update ```data.speed``` in application yml, default speed is 20 Km/h
- **Input service**: Update value of ```data.mode``` to config / local
