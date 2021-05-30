package basketdemo1.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.BasketItemEntity;
import basketdemo1.entities.ProductEntity;
import basketdemo1.enumeration.OrderStatus;

public interface BasketServiceInterface {

	public BasketEntity save(BasketEntity item);
	public void delete(BasketEntity item);
	public List<BasketEntity> getAllBaskets();
	public BasketEntity getBasketById(Long id);
	public BasketEntity getActiveBasketById(Long id);
	public Optional<BasketEntity> getBasketByCustomerIdAndOrderStatus(String username, OrderStatus orderStatus);		
	public BasketItemEntity addBasketItem(ProductEntity product, String username);
	public void removeBasketItem(Long productId, String username);
	public void updateBasketItem(BasketItemEntity newBasketItem, Long productId);
	public BigDecimal calculateTotalPrice(OrderStatus orderStatus);
}
