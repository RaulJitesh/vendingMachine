package com.example.vending.vendingMachine.model;

public class Cart<E1, E2> {
	private E1 item;
	private E2 coins;

	public Cart(E1 item, E2 coins) {
		this.item = item;
		this.coins = coins;
	}

	public E1 getItem() {
		return item;
	}

	public void setItem(E1 item) {
		this.item = item;
	}

	public E2 getCoins() {
		return coins;
	}

	public void setCoins(E2 coins) {
		this.coins = coins;
	}
}