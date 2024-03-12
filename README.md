# GrowWithMe

Techstack: MySQL, Spring Web, Spring Data JPA, Spring Security, OAuth2, Maven, Lombok

Backend application for personal trainers to collaborate with clients. It enables the creation of a database of exercises, meals, and survey questions. Each trainer can utilize existing records and expand upon them. By utilizing this data, the trainer can create training plans, dietary plans, and preference surveys for their clients. Provided endpoints include:
-Registration
-Login
-Management of previously created plans and surveys
-Assigning previously created products to new clients
-Displaying assigned datasets by the client

To start the application:
Clone the project and open it in your preferred Integrated Development Environment (IDE).
Add a file named application.properties in the resources folder with the following configuration:

spring.datasource.url= "databaseurl"
spring.datasource.username= yourUsername
spring.datasource.password= yourPassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

Ensure you replace "databaseurl" with the actual URL of your database.

After adding the application.properties file with the specified configuration, you should be able to run the application.
