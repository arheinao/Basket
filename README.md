### Tests
There are two ways to test the code.

1) BasketControllerTests class (basketdemo1 package) contains 3 unit tests:
    * Adding a basket item - Test through rest controller using a mock rest service
    * Removing non-existing basket item - Tests if exception is thrown
    * Calculation of total price - Unit test of basket service returning the total price

2) By running the Spring Boot App a H2 in-memory database is created and initialized through LoadDatabase class. Perform the following requests:

    A. Fetching all basket items

         curl http://localhost:8080/basket/items

      Response will be:
      [{"id":7,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":0.00},"product":{"id":1,"title":"Marina's socks","price":25.00},"quantity":1},            {"id":8,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":0.00},"product":{"id":2,"title":"Michel's lens","price":5.00},"quantity":1},     {"id":9,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":0.00},"product":{"id":3,"title":"Mama's dress","price":100.00},"quantity":1}]

    B. Adding a new basket item

         Mac curl -d '{"id":1}' -H 'Content-Type: application/json' http://localhost:8080/basket/items
         Windows curl -d "{\"id\":1}" -H "Content-Type: application/json" http://localhost:8080/basket/items

      Info: App initially loads and saves products to in-memory databse. In this way it simulates the real-world situation in which products have to exist before being added to       basket. 

    By adding a new item for product with id=1, quantity is updated to "2"
    curl http://localhost:8080/basket/items
[{"id":7,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":155.00},"product":{"id":1,"title":"Marina's socks","price":25.00},"quantity":2},{"id":8,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":155.00},"product":{"id":2,"title":"Michel's lens","price":5.00},"quantity":1},{"id":9,"basket":{"id":4,"username":"kelavam","orderStatus":"IN_PROGRESS","totalPrice":155.00},"product":{"id":3,"title":"Mama's dress","price":100.00},"quantity":1}]


    C. Removing a new basket item

      curl -X DELETE http://localhost:8080/basket/items/1

    Info: Completely removes the product, regardless on quantity.

    Check with curl http://localhost:8080/basket/items


    D. Getting a total price

        curl http://localhost:8080/basket/4/total-price


    E. Updating the item quantity.

        Mac curl -X PUT http://localhost:8080/basket/items/2 -H 'Content-Type: application/json' -d '{"id":1, "quantity":200}'
        Windows curl -X PUT http://localhost:8080/basket/items/2 -H "Content-Type: application/json" -d "{\"id\":1, \"quantity\":200}"

        Check with curl http://localhost:8080/basket/items
