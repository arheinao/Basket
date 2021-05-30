package basketdemo1.entities;


import java.math.BigDecimal;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import basketdemo1.enumeration.OrderStatus;

@Entity
public class BasketEntity {

	@GeneratedValue @Id private Long id;
	
	/*@OneToOne(optional = false)
	@Access(AccessType.PROPERTY)	
	private CustomerDetails customerDetails;*/
	
	private String username;
	OrderStatus orderStatus;
	BigDecimal totalPrice;
	
	public BasketEntity() {}
	
	/*public BasketEntity(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;	
	}*/
	
	public BasketEntity(String username, OrderStatus orderStatus) {
		this.username = username;	
		this.orderStatus = orderStatus;
		this.totalPrice = new BigDecimal(0);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	@Override
	public String toString() {
		return "Basket{" + "id=" + this.id + ", username='" + this.username + '\'' + 
				", totalPrice='" + this.totalPrice + ", status= " + this.orderStatus.toString() + '}';
	}
	
	/*public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}*/
	
	
}
