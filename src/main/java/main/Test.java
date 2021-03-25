package main;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<Integer> prices = List.of(515, 961, 128, 49, 318, 26, 183, 655, 319, 189,157);
		int budget = 157;
		System.out.println(getBestPriceCombination(prices, budget));

	}

	private static List<Integer> getBestPriceCombination(List<Integer> prices, int budget) {

		List<List<Integer>> reachedBudgets = getReachedBudgets(budget);

		int closestBudget = 0;

		for (int price : prices) {

			for (int i = budget - price; i >= 0; i--) {

				List<Integer> reachedBudget = reachedBudgets.get(i);

				if (reachedBudget != null && reachedBudgets.get(i + price) == null) {
					List<Integer> priceCombination = new ArrayList<>(reachedBudget);
					priceCombination.add(price);
					reachedBudgets.set(i + price, priceCombination);
					if (i + price > closestBudget) {
						closestBudget = i + price;
					}
				}

			}

		}
		System.out.println(closestBudget);
		return reachedBudgets.get(closestBudget);

	}

	private static List<List<Integer>> getReachedBudgets(int budget) {

		List<List<Integer>> reachedBudgets = new ArrayList<>(budget + 1);

		reachedBudgets.add(new ArrayList<>());

		for (int reachedBudget = 1; reachedBudget < budget + 1; reachedBudget++) {
			reachedBudgets.add(null);
		}

		return reachedBudgets;

	}

}
