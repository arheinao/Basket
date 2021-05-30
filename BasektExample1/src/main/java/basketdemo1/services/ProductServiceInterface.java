package basketdemo1.services;

import java.util.List;

import basketdemo1.entities.ProductEntity;

public interface ProductServiceInterface {
	public ProductEntity getProductById(Long id);
	public List<ProductEntity> getAllProducts();
}
