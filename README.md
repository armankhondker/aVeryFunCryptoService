# A "Very Fun" Crypto Service 🚀

A Crypto web service that uses symmetric encryption and implements several useful APIs: `PushAndRecalculate`, `PushRecalculateAndEncrypt`, and `Decrypt`.

Application Startup
---

1. Run `mvn clean install` to build the application
1. Start the application with `java -jar target/dropwizardProject-1.0-SNAPSHOT.jar server config.yml`
1. To check that the application is running enter url `http://localhost:8080`

Design 
---
#### Constraints 
* We expect an input stream of positive, negative, and zero values. 
* We have limited RAM and for the storage of the input values.
* No client authentication, nor server side TLS/SSL necessary

#### Assumptions
* We will only recieve integer as inputs to our API's that are within the valid range (positive, negative, zero).
* We will only initialize one symmetric key to be used to all of our encryption/decryption operations.
* We don't need to store our API calls into any sort of database. 
* We will only use one initlization vector for all encryption/decryption operations.
* If the applicaton stops, our decrypt call will not function since we wont have access to our previous symmetric key and initialization vector

#### High-Level Components
* We have an API folder to hold all the routing requests for our application. 
* We utilize a main class `CryptoService` to hold all of our core logic and functionality.
* Within our main class, we initialize several public/private variables whos scope depends on their purpose within the application. For example, we initialize several `private static final` variables that will never be utilized outside our main class and won't change. 
* We initialize a **SecretKey** and **Initialization Vector** within our CrytoService constructor that will be used for all calls while the application is running. This ensures that when we instantiate our CryptoService object on application startup we guarentee to use the same secret key for all symmetric encryption within the application. 
* The application has been built with clean coding principles and mantinabiltity/extensiability in mind. We leverage the use of serveral Object-Oriented Programming princples and can easily extend this application to include future features and enhancements if required. 


#### AES Encryption
* [AES](https://www.techtarget.com/searchsecurity/definition/Advanced-Encryption-Standard) Encryption was used over RSA Encryption since we wanted to use symmetric encryption and use only one secret key for both encryption and decryption. 
* Since we will only initialze one secret key and initialization vector during the lifespan of the applicaiton, we can't guarentee that future encrypt/decrypt calls will function once the application has stopped.


#### Base 64 Encoding/Decoding
* Base 64 was used as the Encoding Schema for Encoding and Decoding since this would allow us to send our encrpytion over the wire. For example, without this Base 64 encoding our API output's for the `PushRecalculateAndEncrpyt` call would not be able to be decoded directly. 


#### Additional Functionality
* As I was testing this application, I found it very useful to have the ability to reset our running statistics calculation by hitting an endpoint without restarting the application. I implemented the reset endpoint to help solve this problem. 
* I have added in the structure for our application to implement DB logic
* We can configure the config.yml to specify run configurations for our application depending on the environment

#### Potential Next Steps
* Custom Logging for Error Messages
* Add integration tests 
* DB Connection to store our API calls
* HTTP Error Handling
* Potential Refactoring within the APIs (perhaps consolidate to one API call for both pushes) 
* Custom User Interface for interact with APIs

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
[**Arman Khondker**](https://www.linkedin.com/in/armankhondker) - Software Engineer, armankhondker@gmail.com

