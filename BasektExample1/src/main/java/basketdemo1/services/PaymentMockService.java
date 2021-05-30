package basketdemo1.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import basketdemo1.entities.PaymentDetails;

@Service
public class PaymentMockService implements PaymentServiceInterface {
	
	public PaymentMockService() {}
	
	//mock service
	public Long pay(PaymentDetails paymentDetails, Long basketId, BigDecimal totalPrice) {
		
		//Here goes payment execution. I assume here that payment is successful
		
		return Long.valueOf(1);
	}
}