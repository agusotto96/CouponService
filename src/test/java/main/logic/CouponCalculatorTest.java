package main.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.entity.Coupon;
import main.entity.Item;

public class CouponCalculatorTest {

	private CouponCalculator couponCalculator = new CouponCalculator();

	@Test
	public void basic() {

		List<Item> items = List.of(
				new Item("MLA1", 100.0), 
				new Item("MLA2", 210.0), 
				new Item("MLA3", 260.0), 
				new Item("MLA4", 80.0), 
				new Item("MLA5", 90.0));

		double budget = 500.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 480.0);
		assertEquals(coupon.getItemIds(), List.of("MLA1", "MLA2", "MLA4", "MLA5"));

	}
	
	@Test
	public void noItems() {

		List<Item> items = List.of();

		double budget = 500.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 0.0);
		assertEquals(coupon.getItemIds(), List.of());

	}
	
	@Test
	public void noBudget() {

		List<Item> items = List.of(
				new Item("MLA1", 100.0), 
				new Item("MLA2", 210.0), 
				new Item("MLA3", 260.0), 
				new Item("MLA4", 80.0), 
				new Item("MLA5", 90.0));

		double budget = 0.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 0.0);
		assertEquals(coupon.getItemIds(), List.of());

	}
	
	@Test
	public void AllItems() {

		List<Item> items = List.of(
				new Item("MLA1", 100.0), 
				new Item("MLA2", 210.0), 
				new Item("MLA3", 260.0), 
				new Item("MLA4", 80.0), 
				new Item("MLA5", 90.0));

		double budget = 740.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 740.0);
		assertEquals(coupon.getItemIds(), List.of("MLA1", "MLA2", "MLA3", "MLA4", "MLA5"));

	}
	
	@Test
	public void OneItem() {

		List<Item> items = List.of(
				new Item("MLA1", 100.0), 
				new Item("MLA2", 210.0), 
				new Item("MLA3", 500.0), 
				new Item("MLA4", 80.0), 
				new Item("MLA5", 90.0));

		double budget = 500.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 500.0);
		assertEquals(coupon.getItemIds(), List.of("MLA3"));

	}
	
	@Test
	public void Decimals() {

		List<Item> items = List.of(
				new Item("MLA1", 0.33), 
				new Item("MLA2", 0.33), 
				new Item("MLA3", 0.33), 
				new Item("MLA4", 0.01));

		double budget = 1.0;

		Coupon coupon = couponCalculator.getOptimalCoupon(items, budget);

		assertEquals(coupon.getAmount(), 1.0);
		assertEquals(coupon.getItemIds(), List.of("MLA1", "MLA2", "MLA3", "MLA4"));

	}

}
