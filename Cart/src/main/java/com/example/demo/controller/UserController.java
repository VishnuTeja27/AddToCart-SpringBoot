package com.example.demo.controller;

//import java.util.ArrayList;
import java.util.List;
//import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.model.User;
//import com.example.demo.model.UserNotFoundException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
//import com.sun.org.slf4j.internal.Logger;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import com.example.demo.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	@Autowired
	ItemRepository irepo;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/User")
	public User AddUser(@RequestBody User user)
	{
		repo.save(user);
		logger.info("In Add User");
		return user;
	}
	
	@DeleteMapping("/User/{id}")
	public String deleteUser(@PathVariable Long id)
	{
			Optional<User> deluser=repo.findById(id);
			if(deluser.isEmpty()) return "User not Found";
			
			for(Item delitem:deluser.get().getC_items())
			{
				delitem.getUsers().remove(deluser.get());
			}
			deluser.get().getC_items().clear();
			repo.delete(deluser.get());
			logger.info("User is deleted");

			return "User Deleted";
	}
	
	@PutMapping("/User")
	public User saveOrUpdateUser(@RequestBody User user){
		logger.info("In save or update User");
		repo.save(user);
		return user;
	}
	
	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> GetUser(@PathVariable("id") Long id)
	{
		logger.info("Specific User is requested");
		
		Optional<User> result=repo.findById(id);
		if(result.isEmpty())return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(result.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping("/Users")
	public List<User> GetUsers()
	{
		logger.info("List of Users accessed");
		return repo.findAll();
	}
	
	@PutMapping("/User/add/{id}/{iid}")
	public String addToCart(@PathVariable("id") Long id,@PathVariable("iid") Long iid)
	{
		Optional<User> user=repo.findById(id);
		Optional<Item> item= irepo.findById(iid);
		
		if(item.isEmpty())return "No Item found";
		
		if(user.isPresent()) {
			User curr_user =user.get();
			curr_user.getC_items().add(item.get());
			item.get().getUsers().add(curr_user);
			repo.save(curr_user);
			return "Item added to Cart";
		}
		return "No user Found";
	}
	
	@PutMapping("/User/delete/{id}/{iid}")
	public String removeFromCart(@PathVariable("id") Long id,@PathVariable("iid") Long iid)
	{
		Optional<User> user=repo.findById(id);
		Optional<Item> item= irepo.findById(iid);
		
		if(user.isEmpty())return "No User Found";	
		if(item.isEmpty())return "No Item found";
		
		User curr_user =user.get();
		if(!(curr_user.getC_items().contains(item.get())))return "Item Not Found in Cart";
		curr_user.getC_items().remove(item.get());
		item.get().getUsers().remove(curr_user);
		repo.save(curr_user);
		return "Item removed from Cart";
	}
	

	@GetMapping("/User/{id}/items")
	public List<Item> viewCart(@PathVariable("id") Long id) 
	{
		Optional<User> user= repo.findById(id);
		if(user.isEmpty())return null;
		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//	    filterProvider.addFilter("empFilter",SimpleBeanPropertyFilter.filterOutAllExcept("iid", "iname","category"));
//	    ObjectMapper om = new ObjectMapper();
//	    om.setFilterProvider(filterProvider);
//	    
//	    String result =om.writeValueAsString(user.get().getC_items());
//		String result = user.get().getC_items().toString();
		List<Item> result= user.get().getC_items();
		return result;
	}

	
}
