package com.tms;

import com.tms.services.test.testService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {

        //SpringApplication.run(TmsApplication.class, args);
        ApplicationContext context = SpringApplication.run(TmsApplication.class, args);
        var service = context.getBean(testService.class);
        service.sendEmail();
	}

}
