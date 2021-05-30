package basketdemo1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import basketdemo1.entities.BasketItemEntity;


public interface BasketItemRepository extends JpaRepository<BasketItemEntity, Long> {
	
	@Query("SELECT bi FROM BasketItemEntity bi WHERE bi.product.id=?1 AND bi.basket.id=?2")
	public Optional<BasketItemEntity> findByProductIdandBasketId(Long productId, Long basketEntityId);
	
	@Query("SELECT bi FROM BasketItemEntity bi WHERE bi.basket.id=?1")
	public List<BasketItemEntity> getAllBasketItems(Long basketEntityId);
}
