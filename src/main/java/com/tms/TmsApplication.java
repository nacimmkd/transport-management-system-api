package com.tms;

import com.tms.model.Delivery;
import com.tms.model.enums.DeliveryStatus;
import com.tms.repository.DeliveryRepository;
import com.tms.services.DeliveryService;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {
        SpringApplication.run(TmsApplication.class, args);
	}

}
