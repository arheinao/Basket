package basketdemo1;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.BasketItemEntity;
import basketdemo1.entities.ProductEntity;
import basketdemo1.enumeration.OrderStatus;
import basketdemo1.repositories.BasketItemRepository;
import basketdemo1.repositories.BasketRepository;
import basketdemo1.repositories.ProductRepository;



@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	  @Bean
	  CommandLineRunner initDatabase(
			  ProductRepository productRepository, 
			  BasketRepository basketRepository,
			  BasketItemRepository basketItemRepository) {

	    return args -> {
	    	ProductEntity firstProductEntity = new ProductEntity("Marina's socks", new BigDecimal("25.00"));
	    	ProductEntity secondProductEntity = new ProductEntity("Gorana's lens", new BigDecimal("5.00"));
	    	ProductEntity thirdProductEntity = new ProductEntity("Mama's dress", new BigDecimal("100.00"));
	    	productRepository.save(firstProductEntity);
	    	productRepository.save(secondProductEntity);
	    	productRepository.save(thirdProductEntity);
	    	productRepository.findAll().forEach(product -> log.info("Preloaded " + product));	
	    	
	    	BasketEntity firstBasketEntity = new BasketEntity("kelavam", OrderStatus.IN_PROGRESS);
	    	BasketEntity secondBasketEntity = new BasketEntity("kelavam", OrderStatus.CHECKOUT);
	    	BasketEntity thirdBasketEntity = new BasketEntity("gorana", OrderStatus.IN_PROGRESS);
	    	basketRepository.save(firstBasketEntity);
	    	basketRepository.save(secondBasketEntity);
	    	basketRepository.save(thirdBasketEntity);
	    	basketRepository.findAll().forEach(basket -> log.info("Preloaded " + basket));
	    	
	    	BasketItemEntity firstBasketItemEntity = new BasketItemEntity(firstProductEntity, firstBasketEntity, 1);
	    	BasketItemEntity secondBasketItemEntity = new BasketItemEntity(secondProductEntity, firstBasketEntity, 1);
	    	BasketItemEntity thirdBasketItemEntity = new BasketItemEntity(thirdProductEntity, firstBasketEntity, 1);
	    	basketItemRepository.save(firstBasketItemEntity);
	    	basketItemRepository.save(secondBasketItemEntity);
	    	basketItemRepository.save(thirdBasketItemEntity);
	    	basketItemRepository.findAll().forEach(basketItem -> log.info("Preloaded " + basketItem));
	    };
	  }
}