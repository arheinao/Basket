package basketdemo1.services;

public class BasketItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 190965710240759290L;

	public BasketItemNotFoundException() {
		super("BasketItemNotFoundException");
	}
}
