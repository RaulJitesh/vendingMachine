package com.example.vending.vendingMachine.repository;

import com.example.vending.vendingMachine.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem,Long>{

	UserItem findByName(String name);
	List<UserItem> findAll();
}
