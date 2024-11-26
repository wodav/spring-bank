## Open Api generator plugin
https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin

## OpenApi vs Swagger
https://blog.postman.com/openapi-vs-swagger/

## Swagger Editor
https://editor.swagger.io

## Why Delegate Pattern
https://stackoverflow.com/questions/66294655/significance-of-delegate-design-pattern-in-swagger-generated-code

## Why interface Only better then delegate Pattern
Delegate Pattern: After Code generation Controller Files have to be removed
The Controller files also have @Controller (MVC) annotation
Here @RestController is needed.
So interface only creates just interface and then @RestController files are generated manually
and implements interface

## Swgger UI
http://localhost:8080/swagger-ui/index.html#/

Build interface and models from api.yaml
mvn clean install