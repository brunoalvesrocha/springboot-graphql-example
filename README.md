To run the project: 
``./mvnw spring-boot:run``

Head over to ``http://localhost:8080/gui`` and you should see a web UI to test your GraphQL API.

Run a sample query by typing on the left-side panel:
```
{
     foods {
       id
       name
       isGood
     }
   }
```
You can also find a specific food by ID using a query like the following:
``` 
{
    food(id: 1) {
        name
    }
}
```

Create a new food by running the ` saveFood()`  mutation:
``` 
mutation {
  saveFood(food: { name: "Pasta" }) {
    id
    isGood
  }
}
```