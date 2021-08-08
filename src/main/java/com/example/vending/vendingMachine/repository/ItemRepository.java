package com.example.vending.vendingMachine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vending.vendingMachine.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
	
	  Item findByName(String name);
	  List<Item> findAll();
}
