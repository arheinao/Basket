package basketdemo1.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import basketdemo1.entities.PaymentDetails;

@Service
public class PaymentService{
	
	public PaymentService() {}
	
	//mock service
	public Long pay(PaymentDetails paymentDetails, Long basketId, BigDecimal totalPrice) {
		
		//here goes payment execution
		
		return Long.valueOf(1323);
	}
}