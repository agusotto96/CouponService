package main.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import main.model.Item;
import main.presentation.Response;

@Service
public class ItemDataHandler {

	private ItemRepository cache;
	private RestTemplate restTemplate;
	private final long rate = 10;
	private final String baseUrl = "https://api.mercadolibre.com/items";
	private final String urlParameterKey = "?ids=";

	private ItemDataHandler(ItemRepository cache, RestTemplate restTemplate) {
		super();
		this.cache = cache;
		this.restTemplate = restTemplate;
	}

	public List<Item> getItems(Set<String> ids) {

		List<Item> items = getCachedItems(ids);

		if (items.size() < ids.size()) {
			items.forEach(item -> ids.remove(item.getId()));
			items.addAll(getLiveItems(ids));
		}

		items.forEach(item -> item.update());

		saveToCache(items);

		return items;

	}

	@Scheduled(fixedRate = rate * 1000)
	private void cleanCache() {
		cache.deleteBylastUpdateLessThan(LocalDateTime.now().minusSeconds(rate));
	}

	public List<Item> getCachedItems(Set<String> ids) {
		return (List<Item>) cache.findAllById(ids);
	}

	private List<Item> getLiveItems(Set<String> ids) {

		String urlParameterValues = ids.stream().collect(Collectors.joining(","));
		String url = baseUrl + urlParameterKey + urlParameterValues;

		ResponseEntity<Response[]> responses;
		try {
			responses = restTemplate.getForEntity(url, Response[].class);
		} catch (RestClientException e) {
			return new ArrayList<>();
		}

		List<Item> items = Arrays.stream(responses.getBody())
				.filter(response -> response.getCode() != HttpStatus.ACCEPTED.value())
				.map(response -> response.getBody()).collect(Collectors.toList());

		return items;

	}

	private void saveToCache(List<Item> items) {
		cache.saveAll(items);
	}

}
