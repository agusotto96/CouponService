package main.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import main.entity.Item;

@Service
class ItemApiConsumer {

	private RestTemplate restTemplate = new RestTemplate();
	private final String baseUrl = "https://api.mercadolibre.com/items";
	private final String urlParameterKey = "ids";

	List<Item> getLiveItems(List<String> ids) {

		String url = constructUrl(ids);

		ItemWrapper[] itemWrappers = getItemWrappers(url);

		List<Item> items = unwrapValidItems(itemWrappers);

		return items;

	}

	private String constructUrl(List<String> parameters) {
		String urlParameterValues = parameters.stream().collect(Collectors.joining(","));
		String url = baseUrl + "?" + urlParameterKey + "=" + urlParameterValues;
		return url;
	}

	private ItemWrapper[] getItemWrappers(String url) {
		return restTemplate.getForEntity(url, ItemWrapper[].class).getBody();
	}

	private List<Item> unwrapValidItems(ItemWrapper[] itemWrappers) {

		List<Item> items = Arrays.stream(itemWrappers)
				.filter(wrapper -> wrapper.getCode() == HttpStatus.OK.value())
				.map(wrapper -> wrapper.getBody())
				.collect(Collectors.toList());

		return items;

	}

	private static class ItemWrapper {

		private int code;
		private Item body;

		public int getCode() {
			return code;
		}

		public Item getBody() {
			return body;
		}

	}

}
