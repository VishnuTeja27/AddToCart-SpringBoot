package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Items")
//@JsonFilter("empFilter")
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long iid;
	String iname;
	String category;
	
//	@JsonManagedReference
	@JsonIgnore
	@ManyToMany(targetEntity = User.class,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="Cart",joinColumns= @JoinColumn(name="item_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> users= new ArrayList<>();
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Long getIid() {
		return iid;
	}
	@Override
	public String toString() {
		return "Item [iid=" + iid + ", iname=" + iname + ", category=" + category + "]";
	}
	public void setIid(Long iid) {
		this.iid = iid;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
