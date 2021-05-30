package basketdemo1.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.BasketItemEntity;
import basketdemo1.entities.ProductEntity;
import basketdemo1.enumeration.OrderStatus;
import basketdemo1.services.BasketItemService;
import basketdemo1.services.BasketService;
import basketdemo1.utilities.Users;

@RestController
public class BasketController {
	
	private BasketService basketService;	
	private BasketItemService basketItemService;
	
	public BasketController(BasketService basketService, BasketItemService basketItemService) {
		this.basketService = basketService;
		this.basketItemService = basketItemService;
	}
	
	@GetMapping("/basket")
	public BasketEntity getActiveBasketByCustomer() {
		return basketService.getBasketByCustomerIdAndOrderStatus(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS).get(); 
	}
	
	@GetMapping("/baskets")
	public List<BasketEntity> getAllBaskets() {
		return basketService.getAllBaskets(); 
	}

	@GetMapping("/basket/{basketId}")
	public BasketEntity getActiveBasketById(@PathVariable Long basketId) {
		Long basketIdLong = Long.valueOf(basketId);
		return basketService.getActiveBasketById(basketIdLong);
	}
	
	@GetMapping("/basket/items")
	public List<BasketItemEntity> getBasketItemsForActiveUser(){
		return basketItemService.getAllBasketItems(getActiveBasketByCustomer().getId());
	}
	
	@PostMapping("/basket/items")
	public ResponseEntity<?> addBasketItem(@RequestBody ProductEntity product) {
		BasketItemEntity basketItem = basketService.addBasketItem(product, Users.getCurrentUsername());
		if (basketItem == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(basketItem.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
				
	@PutMapping("basket/items/{productId}")
	public ResponseEntity<?> updateBasketItem(@RequestBody BasketItemEntity basketItem, @PathVariable String productId) {
		Long productIdLong = Long.valueOf(productId);
		basketService.updateBasketItem(basketItem, productIdLong);			
		return ResponseEntity.noContent().build();		
	}
	
	@DeleteMapping("/basket/items/{productId}")
	public void removeBasketItem(@PathVariable String productId) {
		Long productIdLong = Long.valueOf(productId);
		basketService.removeBasketItem(productIdLong, Users.getCurrentUsername());  
	}
	
	@GetMapping("/basket/{basketId}/total-price")
	public BigDecimal getBasketTotalPrice(@PathVariable String basketId){
		Long basketIdLong = Long.valueOf(basketId);
		OrderStatus basketStatus = basketService.getBasketById(basketIdLong).getOrderStatus();
		return basketService.calculateTotalPrice(basketStatus);
	}
		
}
