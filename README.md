# A Very Fun Crypto Service 

A Crypto web service that uses symmetric encryption and implements several useful APIs: 
1. PushAndRecalculate
2. PushRecalculateAndEncrypt
3. Decrypt

Application Startup
---

1. Run `mvn clean install` to build the application
1. Start the application with `java -jar target/dropwizardProject-1.0-SNAPSHOT.jar server config.yml`
1. To check that the application is running enter url `http://localhost:8080`

Design 
---
#### Constraints 
* We an accept an input stream of positive, negative, and zero values. 
* We have limited RAM and for the storage of the input values.
* No client authentication, nor server side TLS/SSL necessary

#### Assumptions
* We will only recieve integer as inputs to our API's that are within the valid range.
* We will only initialize one symmetric key to be used to all of our encryption/decryption operations.
* 


#### Additional Functionality
* As I was testing this application, I found it very useful to have the ability to reset our running statistics calculation by hitting an endpoint without restarting the application. I implemented the reset endpoint to help solve this problem. 
* I have added in the strucutre for our application to implemented DB logic and 

#### Future Enhancements
* Custom Logging for Error Messages
* Add integration tests 
* DB Connection to store our running statistics
* HTTP Error Handling 


#### Libaries Utilized
* [Apache DescriptiveStatistics](https://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.html) - Calculate Mean and Standard Deviation
* [Javax Crypto](https://docs.oracle.com/javase/7/docs/api/javax/crypto/package-summary.html) - Symmetric Encryption
* [Java Security](https://docs.oracle.com/javase/8/docs/api/java/security/SecureRandom.html) - Secret Key and Random IV Generation
* [Java Base 64](https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html) - Base 64 encoding and decoding 


Testing
---
1. To test the Application and run all Unit Tests use: `mvn clean test`
2. You can also startup the application locally and hit the endpoints below

Endpoints
---
#### PushAndRecalculate API 
```
curl -L -X POST 'localhost:8080/push' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_NUM}'
```

#### PushRecalculateAndEncrypt API
```
curl -L -X POST 'localhost:8080/encrypt' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_NUM}'
```

#### Decrypt API 
```
curl -L -X POST 'localhost:8080/decrypt' \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
--data-raw '{INSERT_ENCRYPTED_NUM}'
```
##### Note: You can reset the statistics of the application for while testing by using the reset API: `curl -L -X POST 'localhost:8080/reset'`

Example Input/Output Sequence
---

| API Call | Result (Mean, Standard Deviation)|
| :-------- | :------|
| PushAndRecalculate(4) | 4, 0 |
| PushAndRecalculate(7) | 5.5, ~1.5 |
| PushAndRecalculate(6) | 5.667, ~1.247 |
| PushRecalculateAndEncrypt(6) | encrypted(6.5), encrypted(~1.802) |
| Decrypt(encrypted(6.5)) | 6.5 |
| Decrypt(encrypted(1.802)) | 1.802 |
| PushAndRecalculate(1) | 5.4, ~2.728 |

Technology
---
* [Dropwizard](http://www.dropwizard.io/) - Development Framework
* [Java](https://docs.oracle.com/en/java/) - Language 
* [JUnit5](https://junit.org/junit5/docs/current/user-guide/) - Unit Testing Framework 
* [Maven](https://maven.apache.org/guides/index.html) - Build Management 
* [AES](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard) - Symmetric Encryption 

References
---

* [Java Standard Crypto Algorithms](https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/) 
* [AES Encrpyption](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard)
* [Apache Descriptive Statisitics Libraray Documentation](https://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.html)
* [Standard Deviation Calculations](https://www.thoughtco.com/population-vs-sample-standard-deviations-3126372#:~:text=Qualitative%20Differences&text=The%20population%20standard%20deviation%20is,the%20individuals%20in%20a%20population.)
* [Symmetric vs Asymmetric Encryption](https://www.ssl2buy.com/wiki/symmetric-vs-asymmetric-encryption-what-are-differences#:~:text=Symmetric%20encryption%20uses%20a%20single,and%20decrypt%20messages%20when%20communicating.)

Author
---
**Arman Khondker** - Software Engineer, armankhondker@gmail.com

