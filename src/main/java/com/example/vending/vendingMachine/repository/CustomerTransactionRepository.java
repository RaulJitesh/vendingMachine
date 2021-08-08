package com.example.vending.vendingMachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vending.vendingMachine.model.Transaction;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<Transaction,Long>{

	/*@Query("SELECT t.name FROM Transaction t where t.name = :name")
	String findByName(@Param("name") String name);*/
	Transaction findByName(String name);
}
