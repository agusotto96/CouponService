package main.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ItemDataHandlerConfigurator {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
