# market-api
spring boot rest api 

To create a docker image and run it:
```
git clone https://github.com/MironovAlexanderJR/market-api
cd market-api
.\mvnw clean package -DskipTests
docker build -t market-api .
docker run --name market-api-container 
    -e SPRING_DATASOURCE_URL=your_database_name 
    -e SPRING_DATASOURCE_USERNAME=your_database_username 
    -e SPRING_DATASOURCE_PASSWORD=your_database_password 
    -e SPRING_TEST_DATASOURCE_URL=your_test_database_name 
    -e SPRING_TEST_DATASOURCE_USERNAME=your_test_database_username
    -e SPRING_TEST_DATASOURCE_PASSWORD=your_test_database_password -p 8080:8080 market-api
```