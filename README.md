# Demo-Forum-Post-Microservice

This project includes all the post related function for [demo-forum](https://www.google.com). The microservice is designed to run on secure environment instead of public cloud or any sort.

The member microservice would provide the following service.

  - Creation, editing and deletion post

## API doc
For localhost environment: http://localhost:8080/swagger-ui.html

### Tech

This micoservice deploy the following technology and project

* [Spring boot webflux] - Service code backend
* [Spring security] - Handle login process and generate JWT token
* [Spring data mongodb reactive] - Handle JPA
* [JWT] API authentication and authorisation
* [Docker] Microservice virtualisation
* [Redis] High performance cache service
* [MongoDB] Relational database

### Build

Demo-Forum-Member-Microservice requires gradle v6.5.1+ to run.

```sh
$ gradle build
```
### Run on Docker 
```sh
docker-compose up -d
```

### Swagger

The swagger panel is in 
http://localhost:8080/swagger-ui.html#/member-rest-controller

#### How to test
1. Call login API and get JWT with curl command
    ```sh
    curl -i -H "Content-Type: application/json" -X POST -d '{
        "email": "xxx@yyy.com",
        "password": "abcd1234"
    }' http://localhost:8080/rest/login
    ```
2. Copy the JWT to autherisation tab
eg: Bearer xxx 

*Please refer to [wiki](https://github.com/gstkenpo/demo-forum-micoservice-post/wiki) to check the API details
License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Spring boot webflux]: <https://www.baeldung.com/spring-webflux>
   [Spring data mongodb reactive]: <https://springframework.guru/spring-data-mongodb-with-reactive-mongodb/>
   [Spring security]: <https://spring.io/projects/spring-security>
   [JWT]: <https://jwt.io/>
   [Docker]: <https://www.docker.com/>
   [Redis]: <https://redis.io/>
   [MongoDB]: <https://www.mongodb.com>