package basketdemo1.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.BasketItemEntity;
import basketdemo1.entities.ProductEntity;
import basketdemo1.enumeration.OrderStatus;
import basketdemo1.repositories.BasketRepository;
import basketdemo1.utilities.Users;

@Service
public class BasketService implements BasketServiceInterface {
	
	@Autowired
	private BasketRepository basketRepository;
	private BasketItemService basketItemService;
		
	public BasketService(BasketRepository basketRepository, BasketItemService basketItemService) {
		this.basketRepository = basketRepository;
		this.basketItemService = basketItemService;
	}
	
	@Override
	public BasketEntity save(BasketEntity item) {
		Optional<BasketEntity> existingBasketItem = getBasketByCustomerIdAndOrderStatus(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS);		
		if (existingBasketItem.isPresent())
			return existingBasketItem.get();
		return this.basketRepository.saveAndFlush(item);		
	}
	
	@Override
	public void delete(BasketEntity item) {
		this.basketRepository.delete(item);
	}	
			
	public List<BasketEntity> getAllBaskets(){
		return basketRepository.findAll();
	}

	@Override
	public BasketItemEntity addBasketItem(ProductEntity product, String username) {
		BasketItemEntity basketItem;
		BasketEntity basket = getBasketByCustomerIdAndOrderStatus(username, OrderStatus.IN_PROGRESS).get();
		Optional<BasketItemEntity> basketItemOpt = basketItemService.findByProductIdandBasketId(product.getId(), basket.getId());		
		if(!basketItemOpt.isPresent()) {
			basketItem = new BasketItemEntity(product, basket, 1);
		}else {
			basketItem = basketItemOpt.get();
			int currentQuantity = basketItem.getQuantity();
			basketItem.setQuantity(currentQuantity + 1);			
		}
		basketItemService.save(basketItem);
		calculateTotalPrice(OrderStatus.IN_PROGRESS);
		return basketItem;
	}
	
	public void removeBasketItem(Long productId, String username) throws ProductNotFoundException {
		BasketEntity basket = getBasketByCustomerIdAndOrderStatus(username, OrderStatus.IN_PROGRESS).get();
		Optional<BasketItemEntity> basketItem = basketItemService.findByProductIdandBasketId(productId, basket.getId());
		if(!basketItem.isEmpty()) {
			basketItemService.removeItem(basketItem.get());
			calculateTotalPrice(OrderStatus.IN_PROGRESS);
		}else {
			throw new ProductNotFoundException(productId);
		}
	}
	
	public void updateBasketItem(BasketItemEntity newBasketItem, Long productId) throws ProductNotFoundException {
		Optional<BasketEntity> activeBasket = getBasketByCustomerIdAndOrderStatus(Users.getCurrentUsername(), OrderStatus.IN_PROGRESS); 	
		Optional<BasketItemEntity> basketItem = basketItemService.findByProductIdandBasketId(productId, activeBasket.get().getId());
		if(!basketItem.isEmpty()) {
			basketItemService.updateBasketItem(newBasketItem, basketItem.get().getId());
			calculateTotalPrice(OrderStatus.IN_PROGRESS);
		}else {
			throw new ProductNotFoundException(productId);
		}		
	}
	
	@Override
	public BasketEntity getBasketById(Long id) {
		BasketEntity basketEntity;
		basketEntity = basketRepository.findById(id)
				.orElseThrow(() -> new BasketNotFoundExeption(id));
		return basketEntity;
	}
	
	@Override
	public BasketEntity getActiveBasketById(Long id) {
		BasketEntity basketEntity;
		basketEntity = basketRepository.findById(id)
				.orElseThrow(() -> new BasketNotFoundExeption(id));
		if (!basketEntity.getOrderStatus().equals(OrderStatus.IN_PROGRESS))
			throw new BasketNotActiveExeption(id);
		return basketEntity;
	}

	@Override
	public Optional<BasketEntity> getBasketByCustomerIdAndOrderStatus(String username, OrderStatus orderStatus) {
		return basketRepository.getBasketByCustomerIdandOrderStatus(username, orderStatus);
	}
	
	@Override
	public Optional<BasketEntity> getBasketByBasketIdAndOrderStatus(Long basketId, OrderStatus orderStatus) {
		return basketRepository.getBasketByBasketIdandOrderStatus(basketId, orderStatus);
	}
	
	@Override
	public BigDecimal calculateTotalPrice(OrderStatus orderStatus) {
		Optional<BasketEntity> basket = getBasketByCustomerIdAndOrderStatus(Users.getCurrentUsername(), orderStatus);
		List<BasketItemEntity> basketItems = basketItemService.getAllBasketItems(basket.get().getId());
		BigDecimal totalPriceTmp = new BigDecimal("0.00");
		for (BasketItemEntity basketItemEntity : basketItems) {
			totalPriceTmp = totalPriceTmp.add(
					basketItemEntity.getProduct().getPrice().multiply(new BigDecimal(basketItemEntity.getQuantity())));
		}
		
		basket.get().setTotalPrice(totalPriceTmp);
		basketRepository.save(basket.get());
		
		return totalPriceTmp;
	}

}
