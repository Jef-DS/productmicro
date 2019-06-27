package org.betavzw.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;
@Component
public class ProductUpdListener {
	@Autowired
	ProductService prodService;
	
	@JmsListener(destination="${jms.ProductTopic", subscription = "productSearchListener")
	public void receiveMessage(ProductUpdMsg msg) {
		Product product = msg.getProduct();
		boolean isDelete = msg.isDelete;
		if (isDelete) {
			prodService.deleteProduct(product);
			System.out.println("deleted " + product.getId());
		}else {
			prodService.insertUpdateProduct(product);
			System.out.println("inserted/updated " + product.getId());
		}
	}
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}
