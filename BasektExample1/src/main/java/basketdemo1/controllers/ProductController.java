package basketdemo1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import basketdemo1.entities.ProductEntity;
import basketdemo1.services.ProductService;

@RestController
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@GetMapping("/products")
	public List<ProductEntity> getProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public ProductEntity getProduct(@PathVariable String id) {
		Long productIdLong = Long.valueOf(id);
		return productService.getProductById(productIdLong);
	}
		
}
