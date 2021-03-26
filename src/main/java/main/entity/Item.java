package main.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

	@Id
	private String id;
	private Double price;
	private LocalDateTime lastUpdate;

	public Item() {
		super();
	}

	public Item(String id, Double price) {
		super();
		this.id = id;
		this.price = price;
		this.lastUpdate = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void update() {
		this.lastUpdate = LocalDateTime.now();
	}

}
