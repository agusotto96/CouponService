package com.meli.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.entity.Coupon;
import main.entity.Item;
import main.logic.CouponCalculator;

public class CouponCalculatorTest {

	private CouponCalculator couponCalculator = new CouponCalculator();

	@Test
	public void testOptimalCoupon() {

		List<Item> items = List.of(
				new Item("MLA1", 100.0), 
				new Item("MLA2", 210.0), 
				new Item("MLA3", 260.0), 
				new Item("MLA4", 80.0), 
				new Item("MLA5", 90.0));

		double budget = 500.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 480.0);
		assertEquals(coupon.getItemsIds(), List.of("MLA1", "MLA2", "MLA4", "MLA5"));

	}

}
