package main.model;

import java.util.List;

public class Coupon {

	List<String> itemIds;
	double amount;

	public Coupon(List<String> itemIds, double amount) {
		super();
		this.itemIds = itemIds;
		this.amount = amount;
	}

	public List<String> getItemsIds() {
		return itemIds;
	}

	public double getAmount() {
		return amount;
	}

}
