package com.example.vending.vendingMachine.controller;

import java.util.List;

import com.example.vending.vendingMachine.model.*;
import com.example.vending.vendingMachine.service.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@RestController
@RequestMapping("/api/")
public class VendingMachineController {
	
	@Autowired
	@Qualifier("vendingMachineService")
	VendingMachineService vendingMachineService;
	
	  @GetMapping("/allitems")
	  public List<Item> getAllItems() {
		  return vendingMachineService.findAllItems();  
	  }
	  
	  @GetMapping("/allcoins")
	  public List<Coin> getAllCoins() {
		  return vendingMachineService.findAllCoins();  
	  }
	  
	  @PostMapping("/setupVendingMachine")
	  public ResponseEntity<String> setupVendingMachine(@RequestBody String requestJson) {
		  ObjectMapper objectMapper = new ObjectMapper();
		  VendingMachine vendingMachine = null;
		try {
			vendingMachine = objectMapper.readValue(requestJson, VendingMachine.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return ResponseEntity.ok(vendingMachineService.createMachine(vendingMachine));
	  }
	  
	  @GetMapping("/getPriceOfItem/{name}")
	  public ResponseEntity<String> selectAndGetPrice(@PathVariable(value = "name") String name) {
		  return ResponseEntity.ok(vendingMachineService.selectItemAndGetPrice(name));
	  }
	  
	  @PostMapping("/insertCoin")
	  public ResponseEntity<String> insertCoin(@RequestBody UserCoins UserCoins) {
		  return ResponseEntity.ok(vendingMachineService.insertCoin(UserCoins));
	  }
	  
	  @PostMapping("/collectItemAndChange")
	  public ResponseEntity<Cart<Item, List<UserCoins>>> collectItemAndChange(@RequestBody Item item) {
		  return ResponseEntity.ok(vendingMachineService.collectItemAndChange(item));
	  }
	  
}
