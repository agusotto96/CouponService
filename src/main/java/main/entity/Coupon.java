package main.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coupon {

	@JsonProperty("item_ids")
	private List<String> itemIds;
	private double amount;

	public Coupon(List<String> itemIds, double amount) {
		super();
		this.itemIds = itemIds;
		this.amount = amount;
	}

	public List<String> getItemIds() {
		return itemIds;
	}

	public double getAmount() {
		return amount;
	}

}
