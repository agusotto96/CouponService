package main.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import main.entity.Coupon;
import main.entity.Item;

@Service
public class CouponCalculator {

	public Coupon getOptimalCoupon(List<Item> items, double budget) {

		int closestBudget = 0;
		int budgetInCents = (int) (budget * 100);
		Map<Integer, List<String>> reachedBudgets = new HashMap<>(budgetInCents + 1);
		reachedBudgets.put(0, new ArrayList<>());

		for (Item item : items) {

			int priceInCents = (int) (item.getPrice().doubleValue() * 100);

			for (int i = budgetInCents - priceInCents; i >= 0; i--) {

				if (reachedBudgets.containsKey(i) && !reachedBudgets.containsKey(i + priceInCents)) {

					List<String> priceCombination = new ArrayList<>(reachedBudgets.get(i));
					priceCombination.add(item.getId());
					reachedBudgets.put(i + priceInCents, priceCombination);

					if (i + priceInCents > closestBudget) {
						closestBudget = i + priceInCents;
					}

				}

			}

		}

		return new Coupon(reachedBudgets.get(closestBudget), closestBudget / 100.0);

	}

}
