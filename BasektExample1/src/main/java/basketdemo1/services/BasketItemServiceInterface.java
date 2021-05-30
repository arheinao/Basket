package basketdemo1.services;

import java.util.List;
import java.util.Optional;

import basketdemo1.entities.BasketItemEntity;

public interface BasketItemServiceInterface {
	
	public BasketItemEntity save(BasketItemEntity item);
	public void removeItem(BasketItemEntity item);
	public Optional<BasketItemEntity> findByProductIdandBasketId(Long productId, Long basketEntityId);
	public List<BasketItemEntity> getAllBasketItems(Long basketEntityId);
	public BasketItemEntity updateBasketItem(BasketItemEntity basketItemEntity, Long basketItemId);
}
