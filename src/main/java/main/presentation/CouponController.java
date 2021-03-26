package main.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import main.data.ItemDataHandler;
import main.entity.Coupon;
import main.entity.Item;
import main.logic.CouponCalculator;

@RestController
public class CouponController {

	private ItemDataHandler itemDataHandler;
	private CouponCalculator couponCalculator;

	CouponController(ItemDataHandler consumer, CouponCalculator couponCalculator) {
		super();
		this.itemDataHandler = consumer;
		this.couponCalculator = couponCalculator;
	}

	@PostMapping("coupon")
	Coupon getItem(@RequestBody Coupon coupon) {

		try {

			List<Item> items = itemDataHandler.getItems(coupon.getItemsIds());

			Coupon optimalCoupon = couponCalculator.getOptimalCoupon(items, coupon.getAmount());

			if (optimalCoupon.getItemsIds().size() == 0) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

			return optimalCoupon;

		} catch (RestClientException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

	}

}
