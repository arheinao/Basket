package basketdemo1.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DynamicPriceEntity {
	
	@GeneratedValue @Id
	private Long id;

	private Date startDateTime;
	private Date endDateTime;
	private BigDecimal price;
	
	public DynamicPriceEntity() {}
	
	public DynamicPriceEntity(Date startDateTime, Date endDateTime, BigDecimal price) {
		super();
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
