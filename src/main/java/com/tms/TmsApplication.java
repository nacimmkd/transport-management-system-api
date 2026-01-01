package com.tms;

import com.tms.services.deliveryService.DeliveryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {
        SpringApplication.run(TmsApplication.class, args);
        //ApplicationContext context = SpringApplication.run(TmsApplication.class, args);
        //var service = context.getBean(DeliveryService.class);
	}

}
