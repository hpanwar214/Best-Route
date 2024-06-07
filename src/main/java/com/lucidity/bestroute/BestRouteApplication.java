package com.lucidity.bestroute;

import com.lucidity.bestroute.service.BestRouteCalculatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("com.lucidity")
//@EnableConfigurationProperties({ConfigRouteDataServiceImpl.class})
public class BestRouteApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BestRouteApplication.class, args);
		BestRouteCalculatorService bestRouteCalculatorService = context.getBean(BestRouteCalculatorService.class);
		bestRouteCalculatorService.computeRoute();
	}

}
