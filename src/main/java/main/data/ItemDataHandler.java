package main.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import main.entity.Item;

@Service
public class ItemDataHandler {

	private ItemRepository itemRepository;
	private RestTemplate restTemplate = new RestTemplate();
	private final long cacheFlushRateMiliseconds = 10000;
	private final String baseUrl = "https://api.mercadolibre.com/items";
	private final String urlParameterKey = "ids";

	private ItemDataHandler(ItemRepository cache) {
		super();
		this.itemRepository = cache;
	}

	@Scheduled(fixedRate = cacheFlushRateMiliseconds)
	private void cleanCache() {
		itemRepository.deleteBylastUpdateLessThan(LocalDateTime.now().minusSeconds(cacheFlushRateMiliseconds / 1000));
	}

	public List<Item> getItems(List<String> ids) {

		List<Item> items = getCachedItems(ids);

		if (items.size() < ids.size()) {
			items.forEach(item -> ids.remove(item.getId()));
			items.addAll(getLiveItems(ids));
		}

		items.forEach(item -> item.update());

		saveToCache(items);

		return items;

	}

	private List<Item> getCachedItems(List<String> ids) {
		return (List<Item>) itemRepository.findAllById(ids);
	}

	private List<Item> getLiveItems(List<String> ids) {

		String urlParameterValues = ids.stream().collect(Collectors.joining(","));
		String url = baseUrl + "?" + urlParameterKey + "=" + urlParameterValues;

		ResponseEntity<ItemWrapper[]> responses;
		try {
			responses = restTemplate.getForEntity(url, ItemWrapper[].class);
		} catch (RestClientException e) {
			return new ArrayList<>();
		}

		List<Item> items = Arrays.stream(responses.getBody())
				.filter(wrapper -> wrapper.getCode() == HttpStatus.OK.value())
				.map(wrapper -> wrapper.getBody()).collect(Collectors.toList());

		return items;

	}

	private void saveToCache(List<Item> items) {
		itemRepository.saveAll(items);
	}

}
