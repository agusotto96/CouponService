package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CouponServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponServiceApplication.class, args);
	}
	
	/*
	 * @Scheduled(fixedRate = 1000)
	 * public void scheduleFixedRateTask() {
	 * 		System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
	 * }
	 */

}
