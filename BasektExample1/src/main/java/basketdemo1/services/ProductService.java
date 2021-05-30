package basketdemo1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import basketdemo1.entities.ProductEntity;
import basketdemo1.repositories.ProductRepository;

@Service
public class ProductService implements ProductServiceInterface{

	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public ProductEntity getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}

	@Override
	public List<ProductEntity> getAllProducts() {		// 
		return productRepository.findAll();
	}

	
	
}
