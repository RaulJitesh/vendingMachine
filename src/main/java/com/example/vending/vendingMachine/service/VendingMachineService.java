package com.example.vending.vendingMachine.service;

import java.util.List;

import com.example.vending.vendingMachine.model.*;
import org.springframework.stereotype.Component;



@Component
public interface VendingMachineService {
	List<Item> findAllItems();
	List<Coin> findAllCoins();
	String createMachine(VendingMachine vendingMachine);
	
	public String selectItemAndGetPrice(String itemName);
	public String insertCoin(UserCoins userCoins);
	public List<Coin> refund();
	public Cart<Item, List<UserCoins>> collectItemAndChange(Item item);
	public void reset();
}
