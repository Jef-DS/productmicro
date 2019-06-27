package org.betavzw.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.betavzw.product.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ProductService {
	@Autowired
	ProductRepository prodRepo;

	
	@RequestMapping("/product/{id}")
	Product getProductEntity(@PathVariable("id") String id) {
		return prodRepo.findById(id).orElse(null);
	}
	
	@RequestMapping("/products")
	List<Product> getProductEntitiesForCategory(@RequestParam("id") int id) {
		return prodRepo.findByCatId(id);
	}
	@RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
	Product updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
		Product existingProduct = prodRepo.findById(id).orElse(null);
		if (existingProduct == null) {
			String errMsg = "Product not found with code " + id;
			throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
		}
		existingProduct.setCatId(product.getCatId());
		existingProduct.setName(product.getName());
		Product savedProduct = prodRepo.save(existingProduct);
		producer.sendUpdate(savedProduct, false);
		return savedProduct;
	}
	@ExceptionHandler(BadRequestException.class)
	void handleBadRequests(BadRequestException bre, HttpServletResponse response) throws IOException{
		int respCode = (bre.errCode == BadRequestException.ID_NOT_FOUND)?HttpStatus.NOT_FOUND.value(): HttpStatus.BAD_REQUEST.value();
		response.sendError(respCode, bre.errCode + ": " + bre.getMessage());
	}

	@Autowired
	ProductMsgProducer producer;
	
	@RequestMapping(value="/product/{id}", method = RequestMethod.DELETE)
	Product deleteProduct(@PathVariable("id") String id) {
		Product existingProduct = prodRepo.findById(id).orElse(null);
		if (existingProduct == null) {
			String errMsg = "Product not found with code " + id;
			throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
		}
		prodRepo.delete(existingProduct);
		producer.sendUpdate(existingProduct, true);
		return existingProduct;
	}

}
