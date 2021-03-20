package main.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import main.data.ItemDataHandler;
import main.model.Coupon;
import main.model.Item;

@RestController
public class Controller {

	private ItemDataHandler fetcher;

	Controller(ItemDataHandler consumer) {
		super();
		this.fetcher = consumer;
	}

	@GetMapping("hello")
	String getItem() {
		return "hi";
	}

	@PostMapping("quote")
	Coupon getItem(@RequestBody Coupon coupon) {
		List<Item> items = fetcher.getItems(coupon.getItemsIds());
		return null;
	}

}
