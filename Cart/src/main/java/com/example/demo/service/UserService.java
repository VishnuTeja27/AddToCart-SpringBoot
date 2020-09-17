//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.example.demo.model.User;
//import java.util.List;
//import java.util.Optional;
//
//import com.example.demo.repository.UserRepository;
//@Service
//public class UserService {
//	@Autowired
//	UserRepository repo;
//	
//	public User AddUser(@RequestBody User user)
//	{
//		repo.save(user);
//		return user;
//	}
//	
//	public String deleteUser(@PathVariable Long id)
//	{
//			User deluser=repo.getOne(id);
//			repo.delete(deluser);
//			return "Deleted";
//	}
//	
//	public User saveOrUpdateUser(@RequestBody User user){
//		repo.save(user);
//		return user;
//	}
//	
//	public Optional<User> GetUser(@PathVariable("id") Long id)
//	{
//		Optional<User> result=repo.findById(id);
//		return result;
//	}
//	
//	
//	public List<User> GetUsers()
//	{
//		return repo.findAll();
//	}
//	
//	
//}
