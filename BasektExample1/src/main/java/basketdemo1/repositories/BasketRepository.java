package basketdemo1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import basketdemo1.entities.BasketEntity;
import basketdemo1.enumeration.OrderStatus;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
		
	@Query("SELECT b FROM BasketEntity b WHERE b.username=?1 AND b.orderStatus=?2")	
	Optional<BasketEntity> getBasketByCustomerIdandOrderStatus(String username, OrderStatus orderStatus);
	
}
