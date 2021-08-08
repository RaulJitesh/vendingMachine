package com.example.vending.vendingMachine.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VendingMachine {
	
	@JsonProperty("coins")
	private List<Coin> coins;

	@JsonProperty("items")
	private List<Item> items;
	
	public List<Coin> getCoins() {
		return coins;
	}
	public void setCoins(List<Coin> coins) {
		this.coins = coins;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}

}
