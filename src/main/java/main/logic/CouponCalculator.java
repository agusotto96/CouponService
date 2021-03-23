package main.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import main.model.Coupon;
import main.model.Item;

@Service
public class CouponCalculator {

	public Coupon calculateBestCombination(List<Item> items, double amount) {

		int budget = (int) amount * 100;
		int[] prices = new int[items.size()];

		for (int i = 0; i < items.size(); i++) {
			prices[i] = (int) items.get(i).getPrice().doubleValue() * 100;
		}

		List<Integer> selectedItemsIndex = calculateOptimalSelection(prices, budget);

		List<String> selectedItemsIds = new ArrayList<>(selectedItemsIndex.size());
		BigDecimal spent = BigDecimal.ZERO;
		for (int index : selectedItemsIndex) {
			selectedItemsIds.add(items.get(index).getId());
			spent = spent.add(BigDecimal.valueOf(items.get(index).getPrice()));
		}

		spent.setScale(2);

		return new Coupon(selectedItemsIds, spent.doubleValue());

	}

	private List<Integer> calculateOptimalSelection(int[] prices, int budget) {

		int[][] dp = new int[prices.length][budget + 1];

		for (int i = 0; i < prices.length; i++) {
			dp[i][0] = 0;
		}

		for (int c = 0; c <= budget; c++) {
			if (prices[0] <= c) {
				dp[0][c] = prices[0];
			}
		}

		for (int i = 1; i < prices.length; i++) {

			for (int c = 1; c <= budget; c++) {

				int profit1 = prices[i] <= c ? prices[i] + dp[i - 1][c - prices[i]] : 0;
				int profit2 = dp[i - 1][c];

				dp[i][c] = Math.max(profit1, profit2);

			}

		}

		List<Integer> selectedWeights = new ArrayList<>();
		int totalProfit = dp[prices.length - 1][budget];

		for (int i = prices.length - 1; i > 0; i--) {

			if (totalProfit != dp[i - 1][budget]) {
				selectedWeights.add(i);
				budget -= prices[i];
				totalProfit -= prices[i];
			}

		}

		if (totalProfit != 0) {
			selectedWeights.add(0);
		}

		return selectedWeights;

	}

}
