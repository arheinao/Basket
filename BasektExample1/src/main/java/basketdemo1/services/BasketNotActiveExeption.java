package basketdemo1.services;

public class BasketNotActiveExeption extends RuntimeException {
	
	private static final long serialVersionUID = -2329335305320957482L;

	public BasketNotActiveExeption(Long id) {
		super("BasketNotActiveExeption" + id);
	}

}
