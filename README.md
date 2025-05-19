This project demonstrates a sample implementation of Model Context Protocol (MCP) using Spring AI.

## Install
#### MCP Server
- git clone this sources
```
  git clone https://github.com/mrjishin/spring-ai-mcp-example.git
```
- Create a database in mysql (any other database will do, using JPA)
  Refer to spring-ai-mcp-server-example/docs/schema.sql or set it as "spring.jpa.hibernate.ddl-auto: create" in the application.yml file and run it to create the table.
- Build spring-ai-mcp-server-example (The server source uses QueryDSL, so it must be built first.)
```
spring:
  jpa:
    hibernate:
      ddl-auto: create
```
```
  cd ./spring-ai-mcp-server-example/
  ./gradlew build
```
- Insert sample data by referring to the spring-ai-mcp-server-example/docs/data-en.sql file.
- Start MCP server.
```
  ./gradlew bootRun
```
#### MCP Client
- In application.yml of the spring-ai-mcp-client-example source, change the values ​​of spring.api.openai.api-key to your openai API key.
```
spring:
  ai:
    openai:
      api-key: <YOUR_OPENAI_API_KEY>
```
- Start MCP Client.
```
  cd ./spring-ai-mcp-client-example/
  ./gradlew bootRun
```
- Now you can test it in a web browser.
```
  http://localhost:8888/
```
