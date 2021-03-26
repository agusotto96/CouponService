package main.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import main.entity.Item;

@Service
public class ItemDataHandler {

	private ItemRepository itemRepository;
	private ItemApiConsumer itemApiConsumer;
	private final long cacheFlushRateMiliseconds = 20000;

	ItemDataHandler(ItemRepository itemRepository, ItemApiConsumer itemApiConsumer) {
		super();
		this.itemRepository = itemRepository;
		this.itemApiConsumer = itemApiConsumer;
	}

	@Scheduled(fixedRate = cacheFlushRateMiliseconds)
	private void cleanCache() {
		itemRepository.deleteBylastUpdateLessThan(LocalDateTime.now().minusSeconds(cacheFlushRateMiliseconds / 1000));
	}

	public List<Item> getItems(List<String> ids) {

		List<Item> items = itemRepository.findAllById(ids);

		if (items.size() < ids.size()) {
			items.forEach(item -> ids.remove(item.getId()));
			items.addAll(itemApiConsumer.getLiveItems(ids));
		}

		items.forEach(item -> item.update());

		saveToCache(items);

		return items;

	}

	private void saveToCache(List<Item> items) {
		itemRepository.saveAll(items);
	}

}
