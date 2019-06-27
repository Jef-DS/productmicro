package org.betavzw.product;

public class Product {
	private int id = 1;
	private String name = "Oranges ";
	private int catId = 2;
	public Product() {
		
	}
	public Product(int id) {
		super();
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
}
