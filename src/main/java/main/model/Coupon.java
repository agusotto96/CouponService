package main.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coupon {

	@JsonProperty("item_ids")
	Set<String> itemIds;
	double amount;

	public Set<String> getItemsIds() {
		return itemIds;
	}

	public double getAmount() {
		return amount;
	}

}
