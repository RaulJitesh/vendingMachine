package com.example.vending.vendingMachine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vending.vendingMachine.model.Coin;
@Repository
public interface CoinRepository extends JpaRepository<Coin,Long>{
	
	  Coin findByName(String name);
	  List<Coin> findAll();
}
