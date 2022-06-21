# market-api
spring boot rest api 

Api url: https://void-2086.usr.yandex-academy.ru
Api documentation: https://void-2086.usr.yandex-academy.ru/swagger-ui/index.html

To create a docker image and run it:
```
git clone https://github.com/MironovAlexanderJR/market-api
cd market-api
.\mvnw clean package -DskipTests
docker build -t market-api .
docker run --name market-api-container 
    -e SPRING_DATASOURCE_URL=your_mysql_database_name 
    -e SPRING_DATASOURCE_USERNAME=your_mysql_database_username 
    -e SPRING_DATASOURCE_PASSWORD=your_mysql_database_password 
    -e SPRING_TEST_DATASOURCE_URL=your_test_mysql_database_name 
    -e SPRING_TEST_DATASOURCE_USERNAME=your_test_mysql_database_username
    -e SPRING_TEST_DATASOURCE_PASSWORD=your_test_mysql_database_password -p 80:80 market-api
```

Documentation address(Swagger): http://localhost/swagger-ui/index.html
