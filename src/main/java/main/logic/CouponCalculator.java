package main.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import main.model.Coupon;
import main.model.Item;

@Service
public class CouponCalculator {

	public Coupon getBestPriceCombination(List<Item> items, double budget) {

		int budgetInCents = (int) budget * 100;

		Map<Integer, List<String>> reachedBudgets = new HashMap<>(budgetInCents + 1);

		reachedBudgets.put(0, new ArrayList<>());
		int closestBudget = 0;

		for (Item item : items) {

			int price = (int) item.getPrice().doubleValue() * 100;

			for (int i = budgetInCents - price; i >= 0; i--) {

				if (reachedBudgets.containsKey(i) && !reachedBudgets.containsKey(i + price)) {

					List<String> priceCombination = new ArrayList<>(reachedBudgets.get(i));
					priceCombination.add(item.getId());
					reachedBudgets.put(i + price, priceCombination);

					if (i + price > closestBudget) {
						closestBudget = i + price;
					}

				}

			}

		}

		return new Coupon(reachedBudgets.get(closestBudget), closestBudget / 100.0);

	}

}
