package basketdemo1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import basketdemo1.entities.BasketEntity;
import basketdemo1.entities.PaymentDetails;
import basketdemo1.enumeration.OrderStatus;
import basketdemo1.services.BasketNotFoundExeption;
import basketdemo1.services.BasketService;
import basketdemo1.services.PaymentException;
import basketdemo1.services.PaymentMockService;
import basketdemo1.utilities.Users;

@RestController
public class PaymentController {
		
	BasketService basketService;
	PaymentMockService paymentService;
	
	public PaymentController(BasketService basketService, PaymentMockService paymentService) {		
		this.basketService = basketService;
		this.paymentService = paymentService;
	}
		
	@PostMapping("/checkout/payment")
	public ResponseEntity<Long> pay(@RequestBody PaymentDetails paymentDetails) throws BasketNotFoundExeption, PaymentException {		
		
		BasketEntity basket = basketService.getBasketByCustomerIdAndOrderStatus(Users.getCurrentUsername(), OrderStatus.CHECKOUT)
				.orElseThrow(() -> new BasketNotFoundExeption());
		
		Long paymentId = paymentService.pay(paymentDetails, basket.getId(), basketService.calculateTotalPrice(OrderStatus.CHECKOUT));	
		
		return paymentId!=null?
				new ResponseEntity<Long>(paymentId, HttpStatus.OK) 
				: new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
	}
	
}

