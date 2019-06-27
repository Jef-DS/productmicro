package org.betavzw.product;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.betavzw.product.dao.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductService {
	@RequestMapping("/${env}product/{id}")
	Product getProduct(@PathVariable("id") int id) {
		return new Product(id);
	}

	@RequestMapping("/productIds")
	List<Integer> getProductIds(@RequestParam("id") int id) {
		return Arrays.asList(id + 1, id + 2, id + 3);
	}

	@Autowired
	ProductEntityRepository prodRepo;

	@RequestMapping("/productentity/{id}")
	ProductEntity getProductEntity(@PathVariable("id") int id) {
		return prodRepo.findById(id).orElse(null);
	}

	@RequestMapping("/productentitys")
	List<ProductEntity> getProductEntitiesForCategory(@RequestParam("id") int id) {
		return prodRepo.findByCatId(id);
	}

	@RequestMapping(value = "/productentity", method = RequestMethod.POST)
	ResponseEntity<ProductEntity> insertProductEntity(@RequestBody ProductEntity product) {
		ProductEntity savedProduct = prodRepo.save(product);
		return new ResponseEntity<ProductEntity>(savedProduct, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/productentity/{id}", method = RequestMethod.PUT)
	ResponseEntity<ProductEntity> updateProductEntity(@PathVariable("id") int id, @RequestBody ProductEntity product) {
		// First fetch an existing product and then modify it.
		ProductEntity existingProduct = prodRepo.findById(id).orElse(null);
		if (existingProduct ==  null) {
			return new ResponseEntity<ProductEntity>(HttpStatus.NOT_FOUND);
		}
		// Now update it back
		existingProduct.setCatId(product.getCatId());
		existingProduct.setName(product.getName());
		ProductEntity savedProduct = prodRepo.save(existingProduct);
		// Return the updated product with status ok
		return new ResponseEntity<ProductEntity>(savedProduct, HttpStatus.ACCEPTED);
	}

	/*
	 * @RequestMapping(value = "/productentity/{id}", method = RequestMethod.DELETE)
	 * ResponseEntity<ProductEntity> deleteProduct(@PathVariable("id") int id) {
	 *     ProductEntity existingProduct = prodRepo.findById(id).orElse(null);
	 *     if (existingProduct == null) { 
	 *        return new ResponseEntity<ProductEntity>(HttpStatus.NOT_FOUND); 
	 *     }
	 *     prodRepo.deleteById(id); 
	 *     return new ResponseEntity<ProductEntity>(HttpStatus.ACCEPTED); 
	 * }
	 */
	
	//
	// Exception handling
	//
	@RequestMapping(value="/productentity/{id}", method = RequestMethod.DELETE)
	ProductEntity deleteProduct(@PathVariable("id") int id) {
		ProductEntity existingProduct = prodRepo.findById(id).orElse(null);
		if (existingProduct == null) {
			String errMsg = "Product not found with code " + id;
			throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
		}
		prodRepo.deleteById(id);
		return existingProduct;
	}
	@ExceptionHandler(BadRequestException.class)
	void handleBadRequests(BadRequestException bre, HttpServletResponse response) throws IOException{
		int respCode = (bre.errCode == BadRequestException.ID_NOT_FOUND)?
				HttpStatus.NOT_FOUND.value(): HttpStatus.BAD_REQUEST.value();
		response.sendError(respCode, bre.errCode + ": " + bre.getMessage());
	}
}
