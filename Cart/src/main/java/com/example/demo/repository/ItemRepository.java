package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{

	List<Item> findByCategory(String category);
	
	@Query("from Item where category=?1 order by iname")
	List<Item> findByCategorySorted(String category);
}
