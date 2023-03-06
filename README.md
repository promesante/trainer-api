# trainer-api
Rest API exposing CRUD operations on the Trainer entity
1. save
2. get by id

## How-tos from the command line

### Prerequisites

Tools assumed to be already installed before performing any of the following tasks:

1. JVM
2. Maven


### Building the API

From project's root directory:

`
$> mvn clean install
`

### Running the API 

From project's root directory:

`
$> java -jar target/trainer-api-0.0.1-SNAPSHOT.jar
`

### Manually testing the API

From project's root directory, we invoke each of the folling endpoints of the API using [httpie](https://httpie.io/):

#### save:

```
$> http :8080/trainers < trainer.json

HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Mon, 06 Mar 2023 06:18:56 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "email": "trainer@campgladiator.com",
    "first_name": "Fearless",
    "id": 1,
    "last_name": "Contender",
    "phone": "5125125120"
}
```

#### get all:

```
$> http :8080/trainers

HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Mon, 06 Mar 2023 06:19:07 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "email": "trainer@campgladiator.com",
        "first_name": "Fearless",
        "id": 1,
        "last_name": "Contender",
        "phone": "5125125120"
    }
]
```

#### get by id:

```
$> http :8080/trainers/1

HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Mon, 06 Mar 2023 06:19:12 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "email": "trainer@campgladiator.com",
    "first_name": "Fearless",
    "id": 1,
    "last_name": "Contender",
    "phone": "5125125120"
}
```

### Running the test cases

`$> mvn test
`
