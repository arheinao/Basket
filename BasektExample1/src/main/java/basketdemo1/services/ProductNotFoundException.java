package basketdemo1.services;

public class ProductNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1885255019592630416L;

	public ProductNotFoundException(Long id) {
		super("ProductNotFoundException: " + id);
	}

}
