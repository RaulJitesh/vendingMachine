package com.example.vending.vendingMachine.repository;

import com.example.vending.vendingMachine.model.UserCoins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCoinRepository extends JpaRepository<UserCoins,Long>{

	UserCoins findByName(String name);
	List<UserCoins> findAll();
}
