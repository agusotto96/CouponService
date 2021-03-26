package main.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

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

	@JsonGetter("total")
	public double getAmount() {
		return amount;
	}

	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

	@JsonSetter("amount")
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
