package com.example.vending.vendingMachine.service;

import com.example.vending.vendingMachine.exception.*;
import com.example.vending.vendingMachine.model.*;
import com.example.vending.vendingMachine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("vendingMachineService")
public class VendingMachineServiceImpl implements VendingMachineService{
	
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	UserItemRepository userItemRepository;
	
	@Autowired
	CustomerTransactionRepository customerTransactionRepository;

	@Autowired
	CoinRepository coinRepository;

	@Autowired
	UserCoinRepository userCoinRepository;
	
	
	@Override
	public List<Item> findAllItems() {
		// Returns all the items available in Vending machine
		 return itemRepository.findAll();
	}
	
	@Override
	public List<Coin> findAllCoins() {
		// Returns all the coins available in Vending machine
		 return coinRepository.findAll();
	}


	@Override
	public String createMachine(VendingMachine vendingMachine) {
		// Setup all the Items available in Vending machine
		List<Item> items = vendingMachine.getItems();
		if(items != null) {
			for(Item item: items)
				itemRepository.save(item);
		}

		// Setup all the Coins available in Vending machine
		List<Coin> coins = vendingMachine.getCoins();
		if(coins != null) {
			for(Coin coin: coins)
				coinRepository.save(coin);
		}
		
		return "VENDING MACHINE SETUP DONE SUCCESSFULLY";
		
	}
	
	public String selectItemAndGetPrice(String itemName) {
		String itemPrice = "";
		if (itemName != null) {
			Item item = null;
			item = itemRepository.findByName(itemName);
			if(item != null )
				itemPrice = String.valueOf(item.getPrice());
			else
				itemPrice = "Sold Out, Please buy another item";

		}
		return itemPrice;
	}

	@Override
	public String insertCoin(UserCoins userCoins) {
		Transaction currentTransaction = customerTransactionRepository.findByName(userCoins.getName());
		String currentBalance = "";

		if(currentTransaction != null )
			currentBalance = Double.parseDouble(currentTransaction.getValue()) + userCoins.getDenomination() + "";
		else{
			currentTransaction = new Transaction();
			currentBalance = userCoins.getDenomination() + "";
		}

		currentTransaction.setName(userCoins.getName());
		currentTransaction.setValue(currentBalance);
		currentTransaction.setQuantity(userCoins.getQuantity());

		UserCoins dbUserCoins = userCoinRepository.findByName(userCoins.getName());
		if(dbUserCoins != null){
			dbUserCoins.setQuantity(userCoins.getQuantity() + dbUserCoins.getQuantity());
			dbUserCoins.setName(userCoins.getName());
			dbUserCoins.setDenomination(userCoins.getDenomination()+ dbUserCoins.getDenomination());
		}else {
			dbUserCoins = new UserCoins();
			dbUserCoins.setName(userCoins.getName());
			dbUserCoins.setDenomination(userCoins.getDenomination());
			dbUserCoins.setQuantity(userCoins.getQuantity());
		}
		userCoinRepository.save(dbUserCoins);
		customerTransactionRepository.save(currentTransaction);
		return "COIN INSERTED SUCCESSFULLY";
	}
	
	    @Override
	    public void reset(){
	    	coinRepository.deleteAll();
	    	itemRepository.deleteAll();
	    	customerTransactionRepository.deleteAll();
	    } 
	
		@Override
		public Cart<Item, List<UserCoins>> collectItemAndChange(Item item) {

			// check whether coin inserted or not
			List<UserCoins> dbUserCoins = userCoinRepository.findAll();
			if(dbUserCoins != null){
				Item collectedItem = itemRepository.findByName(item.getName());

				if(collectedItem.getQuantity() >= item.getQuantity())
					collectedItem.setQuantity(collectedItem.getQuantity() - item.getQuantity());
				Double remAmt = 0.0;
				List<Coin> listofcoins = coinRepository.findAll();
				Transaction currentBalanceTransaction = customerTransactionRepository.findByName(dbUserCoins.get(0).getName());
				remAmt = Double.parseDouble(currentBalanceTransaction.getValue()) - item.getPrice() * item.getQuantity();

				if(remAmt < 0) {
					throw new NotSufficientChangeException("Insert more coins of value : = " + remAmt*2 + remAmt);
				}else {
					UserItem userItem = new UserItem();
					userItem.setName(item.getName());
					userItem.setQuantity(item.getQuantity());
					userItem.setPrice(item.getPrice());

					itemRepository.save(collectedItem);
					userItemRepository.save(userItem);

					List<UserCoins> returnCoins = returnCoinsAmount(remAmt,item);
					return new Cart<Item, List<UserCoins>>(item, returnCoins);
				}
			}else{
				throw new NotSufficientChangeException("Insert Coin First... ");
			}
		}

		@Override
		public List<Coin> refund() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<UserCoins> returnCoinsAmount(Double remAmt,Item item){
			List<UserCoins> listOfReturnCoins = new ArrayList<>();
			List<UserCoins> userCoinList = userCoinRepository.findAll();
			for (UserCoins userCoins : userCoinList){
				Coin coin =  coinRepository.findByName(userCoins.getName());
				coin.setDenomination(coin.getDenomination() - userCoins.getDenomination());
				coin.setQuantity(coin.getQuantity() - userCoins.getQuantity());
				coinRepository.save(coin);

				UserCoins returnUserCoins = new UserCoins();
				returnUserCoins.setDenomination((int) (userCoins.getDenomination() - remAmt)); // need to handle properly
				returnUserCoins.setName(userCoins.getName());
				returnUserCoins.setQuantity(userCoins.getQuantity());  // need to handle properly
				listOfReturnCoins.add(returnUserCoins);
			}
		return listOfReturnCoins;
		}
	
}
