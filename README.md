# A Very Fun Crypto Service 

Startup Instructions
---

1. Run `mvn clean install` to build the application
1. Start the application with `java -jar target/dropwizardProject-1.0-SNAPSHOT.jar server config.yml`
1. To check that the application is running enter url `http://localhost:8080`

Design Choice's
---
To view Design Choices made in my implementation, please refer to the design.txt file in the root directory of this project! 


Testing
---
1. To test the Application and run all Unit Tests use: `mvn clean test`
2. You can also startup the application locally and hit the endpoints below

Endpoints
---
### PushAndRecalculate API 
```
curl -L -X POST 'localhost:8080/push' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_NUM}'
```

### PushRecalculateAndEncrypt API
```
curl -L -X POST 'localhost:8080/encrypt' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_NUM}'
```

### Decrypt API 
```
curl -L -X POST 'localhost:8080/decrypt' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_ENCRYPTED_NUM}'
```
##### Note: You can reset the statistics of the application for easier testing by using the reset API: `curl -L -X POST 'localhost:8080/reset'`

Author
---
**Arman Khondker** - Software Engineer, armankhondker@gmail.com

References
---

* [Java Standard Crypto Algorithms](https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/) 
* [AES Encrpyption](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard)
* [Apache Descriptive Statisitics Libraray Documentation](https://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.html)
* [Standard Deviation Calculations](https://www.thoughtco.com/population-vs-sample-standard-deviations-3126372#:~:text=Qualitative%20Differences&text=The%20population%20standard%20deviation%20is,the%20individuals%20in%20a%20population.)
* [Symmetric vs Asymmetric Encryption](https://www.ssl2buy.com/wiki/symmetric-vs-asymmetric-encryption-what-are-differences#:~:text=Symmetric%20encryption%20uses%20a%20single,and%20decrypt%20messages%20when%20communicating.)

