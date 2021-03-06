### Assumptions
* App assumes that a request header contains the credentials to authenticate a user agent with a server. Mock method is [here](https://github.com/arheinao/Basket/blob/master/BasektExample1/src/main/java/basketdemo1/utilities/Users.java).
* Relation between Basket and Customer is 1:1 (customer can have only one active basket).
* Checkout is a process that consist of adding a delivery and billing address. There is no price and item quantity change during the checkout process. I use [enum OrderStatus](https://github.com/arheinao/Basket/blob/master/BasektExample1/src/main/java/basketdemo1/enumeration/OrderStatus.java) to distinct if Basket is in active state (IN_PROGRESS) or in checkout.
* Payment is the final checkout last step (Basket needs to be in CHECKOUT status), so I moved it to [REST BasketController](https://github.com/arheinao/Basket/blob/master/BasektExample1/src/main/java/basketdemo1/controllers/BasketController.java) instead of handling it in separate REST PaymentController. I use a mock payment service.
* The whole [code](https://github.com/arheinao/Basket/tree/master/BasektExample1/src/main/java/basketdemo1) is divided into rest controllers, services, repositories, entities and utilities (helpers) packages. A flow is following: (basket) rest controller -> (basket) service -> (basket) repository -> peform JPA mappning built-in method for saving, finding, deleting (basket) entity.


### Tests
There are two ways to test the code.

1) **As unit tests** 
   
   Link is [here](https://github.com/arheinao/Basket/blob/master/BasektExample1/src/test/java/basketdemo1/BasketControllerTests.java).
   
   BasketControllerTests class (basketdemo1 package) contains 3 unit tests:
    * Adding a basket item. _Test through rest controller using a mock rest service_
    * Removing non-existing basket item. _Tests if exception is thrown_
    * Calculation of total price. _Unit test of basket service returning the total price_

	Results:    
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/BasketTests.png" width="800px" height="auto">  


2) **As curl requests**  
   By running the Spring Boot App a H2 in-memory database is created and initialized through LoadDatabase class. Perform the following requests:

    **A. Fetching all basket items (GET example)**

         curl http://localhost:8080/basket/items

    Response:  
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/GET_all_basket_items.png" width="800px" height="auto">  
      
          
    **B. Adding a new basket item (POST example)**

         Mac curl -d '{"id":1}' -H 'Content-Type: application/json' http://localhost:8080/basket/items
         Windows curl -d "{\"id\":1}" -H "Content-Type: application/json" http://localhost:8080/basket/items

      Info: App initially loads and saves products to in-memory databse. In this way it simulates the real-world situation in which products have to exist before being added to       basket. 

    By adding a new item for product with id=1, quantity is updated to "2"  
         
         curl http://localhost:8080/basket/items
         
	Response:   
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/POST_add_product.png" width="800px" height="auto">        
	
         [{  "id": 7,
            "basket": {
               "id": 4,
               "username": "kelavam",
               "orderStatus": "IN_PROGRESS",
               "totalPrice": 155.00
            },
            "product": {
               "id": 1,
               "title": "Marina's socks",
               "price": 25.00
            },
            "quantity": 2
         },
         {
            "id": 8,
            "basket": {
               "id": 4,
               "username": "kelavam",
               "orderStatus": "IN_PROGRESS",
               "totalPrice": 155.00
            },
            "product": {
               "id": 2,
               "title": "Gorana's lens",
               "price": 5.00
            },
            "quantity": 1
         },
         {
            "id": 9,
            "basket": {
               "id": 4,
               "username": "kelavam",
               "orderStatus": "IN_PROGRESS",
               "totalPrice": 155.00
            },
            "product": {
               "id": 3,
               "title": "Mama's dress",
               "price": 100.00
            },
            "quantity": 1
         }]
         


   **C. Removing a new basket item (DELETE example)**

         curl -X DELETE http://localhost:8080/basket/items/1

    Info: Completely removes the product, regardless on quantity.

         Check with curl http://localhost:8080/basket/items

 Response:   
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/DELETE_product.png" width="800px" height="auto">  

   **D. Getting a total price (GET)**

        curl http://localhost:8080/basket/4/total-price

Response:   
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/GET_total_price.png" width="800px" height="auto">  


   **E. Updating the item quantity (PUT example)**

        Mac curl -X PUT http://localhost:8080/basket/items/2 -H 'Content-Type: application/json' -d '{"id":1, "quantity":200}'
        Windows curl -X PUT http://localhost:8080/basket/items/2 -H "Content-Type: application/json" -d "{\"id\":1, \"quantity\":200}"

        Check with curl http://localhost:8080/basket/items

Response:   
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/PUT_quantity_update.png" width="800px" height="auto">  

   **F. Payment (POST example)**

        Mac curl -d '{}' -H 'Content-Type: application/json' http://localhost:8080/basket/5/checkout/payment  
		Windows curl -d "{}" -H "Content-Type: application/json" http://localhost:8080/basket/5/checkout/payment

Response:   
	<img src="https://github.com/arheinao/BasketTestResults/blob/main/POST_payment.png" width="800px" height="auto"> 


### IDE and dependecies
IDE: Spring Tool Suite 4.4.10.0. release  
Dependencies: JPA, H2, SpringWeb, DevTools

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>


### Materials
[Building rest services with Spring](https://spring.io/guides/tutorials/rest/)  
[Testing in Spring Boot](https://www.baeldung.com/spring-boot-testing)  
[Designing a restful shopping cart](https://nvoulgaris.com/designing-a-restful-shopping-cart/)  
Stackoverflow (mainly for searching how to fix bugs)  
