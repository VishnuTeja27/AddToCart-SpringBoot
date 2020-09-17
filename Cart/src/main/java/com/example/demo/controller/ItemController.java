package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.repository.ItemRepository;

@RestController
public class ItemController {
	@Autowired
	ItemRepository repo;
	
	@PostMapping("/Item")
	public Item AddItem(@RequestBody Item item)
	{
		repo.save(item);
		return item;
	}

	@GetMapping("/getItem/{iid}")
	public ResponseEntity<Item> GetItem(@PathVariable("iid") Long iid)
	{
		Optional<Item> result=repo.findById(iid);
		try {
			return new ResponseEntity<Item>(result.get(),HttpStatus.OK);
		}
		
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Item Not Found",e);
		}
	}
	
	@RequestMapping("/Items")
	public List<Item> GetItems()
	{
		System.out.println(repo.findByCategory("Laptop"));
		System.out.println(repo.findByCategorySorted("Laptop"));

		return repo.findAll();
	}
	@DeleteMapping("/Item/{iid}")
	public String delteItem(@PathVariable("iid") Long iid)
	{
		Optional<Item> delitem=repo.findById(iid);
		if(delitem.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Item Not Found");
		}
		
		for(User deluser:delitem.get().getUsers())
		{
			deluser.getCartItems().remove(delitem.get());
		}
		delitem.get().getUsers().clear();
		repo.delete(delitem.get());
		return "Item Deleted";
		
	}
}
