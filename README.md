# bart (Basic cart)

* 1. Tech-stack: Spring Boot, Hibernate, H2
* 2. For API docs build the project "mvn clean install", run "mvn spring-boot:run" and open "http://localhost:8081/swagger-ui.html#" in browser.
* 3. Test set:
	* 3.1 Create a cart PUT http://localhost:8081/carts/ and remeber your <cart id>.
	* 3.2 Create items and remember your <item id>
```PUT http://localhost:8081/items/ 
{
	"sku": "A",
	"price": 40.00,
	"specialOffer": {
		"qty": 3,
		"price": 70.00 
	}
}
```

```PUT http://localhost:8081/items/ 
{
	"sku": "B",
	"price": 10.00,
	"specialOffer": {
		"qty": 2,
		"price": 15.00 
	}
}
```

```PUT http://localhost:8081/items/ 
{
	"sku": "C",
	"price": 30.00
}
```

```PUT http://localhost:8081/items/ 
{
	"sku": "D",
	"price": 25.00
}
```

	* 3.3 Add items to the cart 

```PUT http://localhost:8081/carts/<cartId>/items 
{
	"id": <itemId>,
	"qty": <quantity>
}
```

	* 3.4 Checkout the cart PUT http://localhost:8081/carts/<cartId>/checkOut
	* 3.5 Check cart balance GET http://localhost:8081/carts/<cartId>/balance
