package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Optional<Item> GetItem(@PathVariable("iid") Long iid)
	{
		Optional<Item> result=repo.findById(iid);
		return result;
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
		if(delitem.isEmpty())return "Item not Present";
		
		for(User deluser:delitem.get().getUsers())
		{
			deluser.getC_items().remove(delitem.get());
		}
		delitem.get().getUsers().clear();
		repo.delete(delitem.get());
		return "Item Deleted";
		
	}
}
