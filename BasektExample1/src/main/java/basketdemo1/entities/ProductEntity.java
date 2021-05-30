package basketdemo1.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductEntity {
	
	@GeneratedValue @Id
	public Long id; 
	
	private String title;
	//private DynamicPriceEntity price;
	private BigDecimal price;
	
	public ProductEntity() {}
	
	public ProductEntity(String title, BigDecimal price) {
		super();
		this.title = title;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	

}
