package com.example.vending.vendingMachine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int denomination;
	
	@Column(unique = true)
	private String name;
	private int quantity;

	public Coin() {
		
	}
	
	public Coin(String name, int denomination,  int quantity) {
		this.denomination = denomination;
		this.name = name;
		this.quantity = quantity;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getDenomination() {
		return denomination;
	}
	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
