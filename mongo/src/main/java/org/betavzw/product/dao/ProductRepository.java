package org.betavzw.product.dao;

import java.util.List;

import org.betavzw.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
	List<Product> findByCatId(int catid);
}
