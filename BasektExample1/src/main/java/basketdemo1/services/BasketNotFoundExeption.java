package basketdemo1.services;

public class BasketNotFoundExeption extends RuntimeException {

	private static final long serialVersionUID = 7147748580508694875L;

	public BasketNotFoundExeption() {
		super("BasketNotFoundExeption");
	}
	
	public BasketNotFoundExeption(Long id) {
		super("BasketNotFoundExeption: " + id);
	}
}
