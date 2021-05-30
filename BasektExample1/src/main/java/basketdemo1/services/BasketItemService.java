package basketdemo1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basketdemo1.entities.BasketItemEntity;
import basketdemo1.repositories.BasketItemRepository;


@Service
public class BasketItemService implements BasketItemServiceInterface{
	
	@Autowired
	private BasketItemRepository basketItemRepository;
		
	public BasketItemService(BasketItemRepository basketItemRepository) {
		this.basketItemRepository =basketItemRepository;
	}
	
	public BasketItemEntity save(BasketItemEntity item) {
		return this.basketItemRepository.save(item);
	}
	
	public void removeItem(BasketItemEntity item) {
		this.basketItemRepository.delete(item);
	}

	@Override
	public Optional<BasketItemEntity> findByProductIdandBasketId(Long productId, Long basketEntityId) {		 
		return basketItemRepository.findByProductIdandBasketId(productId, basketEntityId);
	}

	public List<BasketItemEntity> getAllBasketItems(Long basketEntityId){
		return basketItemRepository.getAllBasketItems(basketEntityId);
	}

	@Override
	public BasketItemEntity updateBasketItem(BasketItemEntity newBasketItem, Long basketItemId) {
		return basketItemRepository.findById(basketItemId)
			      .map(basketItem -> {
			    	  basketItem.setQuantity(newBasketItem.getQuantity());
			        return basketItemRepository.save(basketItem);
			      })
			      .orElseGet(() -> {
			    	  newBasketItem.setId(basketItemId);
			        return basketItemRepository.save(newBasketItem);
			      });
		
	
	}
	
}
