package org.betavzw.product;

public class ProductUpdMsg {
	Product product;
	boolean isDelete = false;
	public ProductUpdMsg() {}
	public ProductUpdMsg(Product product, boolean isDelete) {
		super();
		this.product = product;
		this.isDelete = isDelete;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
