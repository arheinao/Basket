package basketdemo1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import basketdemo1.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
