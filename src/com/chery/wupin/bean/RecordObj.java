package com.chery.wupin.bean;

import java.io.Serializable;

public class RecordObj implements Serializable{
	/**
	 * 录入物品实体类
	 */
	private static final long serialVersionUID = 1L;
	
	private int _id;
	private String name;
	private String catogery;
	private String owner;
	private String come;
	private String time;
	private int price;
	private String address;
	private String comments;//备注
	private int catogery_type_id;
	private int owner_type_id;
	private int address_type_id;
	private String flag; 
	private String year;
	private String reason;

	public void setCount(int count) {
		this.count = count;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCatogery() {
		return catogery;
	}
	
	public void setCatogery(String catogery) {
		this.catogery = catogery;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getCome() {
		return come;
	}
	
	public void setCome(String come) {
		this.come = come;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public int getCatogery_type_id() {
		return catogery_type_id;
	}

	public void setCatogery_type_id(int catogery_type_id) {
		this.catogery_type_id = catogery_type_id;
	}

	public int getOwner_type_id() {
		return owner_type_id;
	}

	public void setOwner_type_id(int owner_type_id) {
		this.owner_type_id = owner_type_id;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	private int count;
	
	public int getCount() {
		return count;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getAddress_type_id() {
		return address_type_id;
	}

	public void setAddress_type_id(int address_type_id) {
		this.address_type_id = address_type_id;
	}

}
