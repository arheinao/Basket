package basketdemo1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.BasketItemEntity;
import basketdemo1.entities.ProductEntity;
import basketdemo1.enumeration.OrderStatus;
import basketdemo1.repositories.BasketItemRepository;
import basketdemo1.repositories.BasketRepository;
import basketdemo1.repositories.ProductRepository;
import basketdemo1.services.BasketService;
import basketdemo1.services.ProductNotFoundException;
import basketdemo1.utilities.JsonUtil;
import basketdemo1.utilities.Users;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BasketControllerTests {

	
	 	@Autowired
	 	private MockMvc mvc;

	 	@Autowired
	 	private BasketRepository basketRepository;
	 	
	 	@Autowired
	 	private BasketService basketService;
	 
	 	@Autowired
	 	private BasketItemRepository basketItemRepository;
	    
	    @Autowired
	    private ProductRepository productRepository;
	    
	    @BeforeEach
	    public void setUp() {
	    	cleanRepositories();
	    }
	    	        	    
	    @AfterEach
	    public void tearDown() {
	    	cleanRepositories();
	    }
	    
	    public void cleanRepositories() {
	    	basketItemRepository.deleteAll();
	    	basketRepository.deleteAll();
	    	productRepository.deleteAll();	
	    }	    
	        
	    @Test
	    public void testAddBasketItem() throws IOException, Exception {
	    	
	    	//Simulate "the real-world scenario" that the product already exists in db (in this case in-memory H2 db)
	    	ProductEntity product = new ProductEntity("Marina's socks", new BigDecimal("25"));
	    	productRepository.saveAndFlush(product);
	    	
	    	//Simulate that the basket already exists in db (in this case in-memory H2 db)
	    	//Users.getCurrentUsername() imitates authorized logged in user
	    	BasketEntity basket = new BasketEntity(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS);
	    	basketRepository.saveAndFlush(basket);
	    	   		    	
	    	//instead of testing the basketService.addItem directly, I decided to test through rest controller using a mock rest service
	    	mvc.perform(post("/basket/items").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(product)));
	    	
	    	//foundBasketItemOpt is created by the post("/basket/items")
	    	Optional<BasketItemEntity> foundBasketItemOpt = basketItemRepository.findByProductIdandBasketId(product.getId(), basket.getId());	    		    		    		    		    	
	    	assertThat(foundBasketItemOpt).isNotEmpty();
	    	
	    	BasketItemEntity foundBasketItem = foundBasketItemOpt.get();
	    	assertThat(foundBasketItem.getQuantity()).isEqualTo(1);	  
	    	assertThat(foundBasketItem.getProduct().getTitle()).isEqualTo(product.getTitle());	
	    }	   
	    
	    @Test
	    public void testRemoveItemNotInCart() {

	    	ProductEntity firstProduct = new ProductEntity("Marina's socks", new BigDecimal("25.00"));
	    	ProductEntity secondProduct = new ProductEntity("Michel's lens", new BigDecimal("5.00"));
	    	ProductEntity thirdProduct = new ProductEntity("Mama's dress", new BigDecimal("100.00"));	    	
	    	productRepository.saveAndFlush(firstProduct);
	    	productRepository.saveAndFlush(secondProduct);
	    	productRepository.saveAndFlush(thirdProduct);
	    	
	    	BasketEntity basket = basketService.save(new BasketEntity(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS));
	    	
	    	basketService.addBasketItem(firstProduct, basket.getUsername());
	    	basketService.addBasketItem(secondProduct, basket.getUsername());
	    	
	    	Throwable throwable =  assertThrows(Throwable.class, () -> {
	    		basketService.removeBasketItem(thirdProduct.getId(), Users.getCurrentUsername());
	    	});
	    	assertEquals(ProductNotFoundException.class, throwable.getClass());
	    	
	    	//Note: an exception could have also been tested through fail("Because of ProductNotFoundException")
	    }
	    
	    
	    @Test
	    public void testBasketTotalPrice() {
	    	
	    	ProductEntity firstProduct = new ProductEntity("Marina's socks", new BigDecimal("25.00"));
	    	ProductEntity secondProduct = new ProductEntity("Michel's lens", new BigDecimal("5.00"));
	    	ProductEntity thirdProduct = new ProductEntity("Mama's dress", new BigDecimal("100.00"));
	    	
	    	productRepository.saveAndFlush(firstProduct);
	    	productRepository.saveAndFlush(secondProduct);
	    	productRepository.saveAndFlush(thirdProduct);
	    		    	
	    	BasketEntity basket = basketService.save(new BasketEntity(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS));
	    	    	
	    	basketService.addBasketItem(firstProduct, basket.getUsername());
	    	basketService.addBasketItem(secondProduct, basket.getUsername());
	    	basketService.addBasketItem(thirdProduct, basket.getUsername());
	    		 	    		    	
	    	BigDecimal basketTotalActual = firstProduct.getPrice()
	    			.add(secondProduct.getPrice())
	    			.add(thirdProduct.getPrice());
	    		    	
	    	assertThat(basketTotalActual).isEqualTo(basketService.calculateTotalPrice(OrderStatus.IN_PROGRESS));	    	
	    }
	    	    	    	   	        	
}
