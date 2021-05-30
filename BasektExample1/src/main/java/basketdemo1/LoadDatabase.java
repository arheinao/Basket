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
	    	ProductEntity pe1 = new ProductEntity("Marina's socks", new BigDecimal("25.00"));
	    	ProductEntity pe2 = new ProductEntity("Michel's lens", new BigDecimal("5.00"));
	    	ProductEntity pe3 = new ProductEntity("Mama's dress", new BigDecimal("100.00"));
	    	productRepository.save(pe1);
	    	productRepository.save(pe2);
	    	productRepository.save(pe3);
	    	productRepository.findAll().forEach(product -> log.info("Preloaded " + product));	
	    	
	    	BasketEntity be1 = new BasketEntity("kelavam", OrderStatus.IN_PROGRESS);
	    	BasketEntity be2 = new BasketEntity("kelavam", OrderStatus.CHECKOUT);
	    	BasketEntity be3 = new BasketEntity("bonansea", OrderStatus.IN_PROGRESS);
	    	basketRepository.save(be1);
	    	basketRepository.save(be2);
	    	basketRepository.save(be3);
	    	basketRepository.findAll().forEach(basket -> log.info("Preloaded " + basket));
	    	
	    	BasketItemEntity bie1 = new BasketItemEntity(pe1, be1, 1);
	    	BasketItemEntity bie2 = new BasketItemEntity(pe2, be1, 1);
	    	BasketItemEntity bie3 = new BasketItemEntity(pe3, be1, 1);
	    	basketItemRepository.save(bie1);
	    	basketItemRepository.save(bie2);
	    	basketItemRepository.save(bie3);
	    	basketItemRepository.findAll().forEach(basketItem -> log.info("Preloaded " + basketItem));
	    };
	  }
}