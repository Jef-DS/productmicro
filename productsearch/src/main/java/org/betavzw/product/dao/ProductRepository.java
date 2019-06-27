package org.betavzw.product.dao;

import java.util.List;

import org.betavzw.product.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
	List<Product> findByCatId(int catId);
}
