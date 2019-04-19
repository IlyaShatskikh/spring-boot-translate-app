# spring-boot-translate-app
(only for educational purposes) 

Translate words and phrases, view your own translate history, search by language.

Yandex Translate API is used. For full functionality, you have to get a free API developer key (https://translate.yandex.com/developers/keys).
 Use key as TRANSLATE_USER_KEY environment variable.

Docker compose .yml file includes two services:
- database (Postgres 9.5);
- translate (Spring Boot application);

The welcome page is localhost:8080

Web view based on html + freemarker templates + bootstrap (no specially written javascript code).
- spring boot web;
- spring jpa;
- spring security;
- spring cache;
- freemarker;
- postgresql;
- lombok;

Using:
<br/>$ git clone ... - clone git repo
<br/>$ cd spring-boot-translate-app
<br/>$ docker-compose up - start containers
<br/>$ curl localhost:8080 - check welcome page

<br/>http://localhost:8080/login - login page
<br/>http://localhost:8080/registration - register new user
<br/>http://localhost:8080/translation - translating page
<br/>http://localhost:8080/user - list of users for admin (editing user's roles)