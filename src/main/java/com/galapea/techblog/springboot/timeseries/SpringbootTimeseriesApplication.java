package com.galapea.techblog.springboot.timeseries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootTimeseriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTimeseriesApplication.class, args);
	}

}
