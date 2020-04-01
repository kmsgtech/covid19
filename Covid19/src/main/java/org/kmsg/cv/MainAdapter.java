package org.kmsg.cv;

import org.kmsg.cv.MainAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "org.kmsg.cv")
@EnableScheduling
public class MainAdapter {
	public static void main(String[] args) 
	{
		SpringApplication.run(MainAdapter.class, args);
	}
}
