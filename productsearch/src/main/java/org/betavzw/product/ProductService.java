package org.betavzw.product;

import java.util.List;

import org.betavzw.product.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ProductService {
	@Autowired
	ProductRepository prodRepo;

	
	@RequestMapping("/products")
	List<Product> getProductEntitiesForCategory(@RequestParam("id") int id) {
		return prodRepo.findByCatId(id);
	}
	@PutMapping("/product/{id}")
	public void insertUpdateProduct(@RequestBody Product product) {
		prodRepo.save(product);
	}
	@DeleteMapping("/product/{id}")
	public void deleteProduct(@RequestBody Product product) {
		prodRepo.delete(product);
	}
}
