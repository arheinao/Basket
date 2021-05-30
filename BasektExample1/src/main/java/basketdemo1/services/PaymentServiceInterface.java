package basketdemo1.services;

import java.math.BigDecimal;

import basketdemo1.entities.PaymentDetails;

public interface PaymentServiceInterface {
	public Long pay(PaymentDetails paymentDetails, Long basketId, BigDecimal totalPrice);
}
