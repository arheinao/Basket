package basketdemo1.entities;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BasketItemEntity {

	@GeneratedValue @Id
	public Long id; 
	
	@ManyToOne
	@Access(AccessType.PROPERTY)
	private BasketEntity basket;
	
	@ManyToOne
	@Access(AccessType.PROPERTY)
	private ProductEntity product;
	
	private Integer quantity;
		
	public BasketItemEntity() {}
	
	public BasketItemEntity(ProductEntity productEntity, BasketEntity basket, Integer quantity) {
		this.product = productEntity;		
		this.basket = basket;
		this.quantity = quantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public ProductEntity getProduct() {
		return product;
	}
	
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	
	public BasketEntity getBasket() {
		return basket;
	}

	public void setBasket(BasketEntity basket) {
		this.basket = basket;
	}
	
	@Override
	public boolean equals(Object obj) {
		
	    if (this == obj)
	      return true;
	    if (!(obj instanceof BasketItemEntity))
	      return false;
	    BasketItemEntity basketItem = (BasketItemEntity) obj;
	    return Objects.equals(this.id, basketItem.id) 
	    		&& Objects.equals(this.getBasket().getId(), basketItem.getBasket().getId())
	    		&& Objects.equals(this.getProduct().id, basketItem.getProduct().getId()) 
	    		&& Objects.equals(this.quantity, basketItem.quantity);
	}
	
	@Override
	public String toString() {
		return "BasketItem{" + "id=" + this.id + ", product='" + this.product.getTitle() + '\'' + ", quantity='" + this.quantity + '}';
	}
	
}
