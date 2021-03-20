package main.data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import main.model.Item;

@Service
public class ItemDataHandler {

	private ItemRepository cache;
	private RestTemplate restTemplate;

	private ItemDataHandler(ItemRepository cache, RestTemplate restTemplate) {
		super();
		this.cache = cache;
		this.restTemplate = restTemplate;
	}

	public List<Item> getItems(Set<String> ids) {

		List<Item> items = getCachedItems(ids);

		if (ids.size() != items.size()) {
			items.forEach(item -> ids.remove(item.getId()));
			items.addAll(getLiveItems(ids));
		}

		saveToCache(items);

		return items;

	}

	private void saveToCache(List<Item> items) {
		cache.saveAll(items);
	}

	private List<Item> getCachedItems(Set<String> ids) {
		return (List<Item>) cache.findAllById(ids);
	}

	private List<Item> getLiveItems(Set<String> ids) {

		String baseUrl = "https://api.mercadolibre.com/items";
		String urlParameters = "?ids=" + ids.stream().collect(Collectors.joining(","));

		ResponseEntity<Item[]> response = restTemplate.getForEntity(baseUrl + urlParameters, Item[].class);
		Item[] items = response.getBody();

		return Arrays.asList(items);

	}

}
