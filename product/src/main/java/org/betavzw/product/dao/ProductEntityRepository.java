package org.betavzw.product.dao;

import java.util.List;

import org.betavzw.product.ProductEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface ProductEntityRepository extends CrudRepository<ProductEntity, Integer> {
	//CrudRepository contains a findById method to get a ProductEntity based on Id
	//findByCatId: query builder mechanism strips 'find' and uses the rest as the name of a property
	@Cacheable("productsByCategoryCache")
	List<ProductEntity> findByCatId(int catId);
	
	//@CacheEvict(cacheNames = "productsByCategoryCache", allEntries = true)
	//Spring Expression Language
	//#result?.catId: extract catId from the return result
	@CacheEvict(cacheNames = "productsByCategoryCache", key = "#result?.catId")
	<S extends ProductEntity> S save(S entity) ;
	
	//@CacheEvict(cacheNames = "productsByCategoryCache", allEntries = true)
	//Spring Expression Language
	//#p0.catId: extract catId from the first parameter
	@CacheEvict(cacheNames = "productsByCategoryCache", key = "#p0.catId")
	void deleteById(Integer id);
}
