package main.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import main.data.ItemDataHandler;
import main.logic.CouponCalculator;
import main.model.Coupon;
import main.model.Item;

@RestController
public class Controller {

	private ItemDataHandler fetcher;
	private CouponCalculator couponCalculator;

	Controller(ItemDataHandler consumer, CouponCalculator couponCalculator) {
		super();
		this.fetcher = consumer;
		this.couponCalculator = couponCalculator;
	}

	@PostMapping("coupon")
	Coupon getItem(@RequestBody Coupon coupon) {
		List<Item> items = fetcher.getItems(coupon.getItemsIds());
		return couponCalculator.getBestPriceCombination(items, coupon.getAmount());
	}

}
