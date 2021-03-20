package main.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private double price;
	private LocalDateTime time = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

}
