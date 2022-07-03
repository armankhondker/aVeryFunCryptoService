![build-passing](https://img.shields.io/badge/build-passing-brightgreen) ![test-passing](https://img.shields.io/badge/test-passing-brightgreen)

# A Very Fun Crypto Service 

Startup Instructions: Crypto Service application
---

1. Run `mvn clean install` to build the application
1. Start the application with `java -jar target/dropwizardProject-1.0-SNAPSHOT.jar server config.yml`
1. To check that the application is running enter url `http://localhost:8080`

Design Choice's
---
To view Design Choices made in my implementation, please refer to the design.txt file in the root directory of this project! 

Endpoints
---

`curl --location --request POST 'localhost:8080/push' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '6'`

Author
---
**Arman Khondker** - Software Engineer, armankhondker@gmail.com

References
---

