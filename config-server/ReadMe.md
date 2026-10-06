🔑 What is a Config Server?

A centralized service that provides configuration properties to all microservices in your system.

Instead of each service keeping its own application.yml or application.properties, 
the Config Server fetches them from a remote source (commonly Git).

Microservices then pull their configuration from the Config Server at startup (and can refresh dynamically).

⚙️ How It Works

1. Config Source (Git, SVN, File System)

Stores configuration files (e.g., user-service.yml, hotel-service.yml).

Each file can have multiple profiles (user-service-dev.yml, user-service-prod.yml).

2. Config Server

Runs as a Spring Boot application with spring-cloud-config-server.

Reads configuration from the source repository.

Exposes REST endpoints like:

/{application}/{profile}
/{application}-{profile}.yml
Example: /user-service/dev → returns config for User Service in dev profile.

3. Client Microservices

Include spring-cloud-starter-config.

At startup, they contact the Config Server to fetch their properties.

They merge those properties with local defaults.

4. Dynamic Refresh

With @RefreshScope and Spring Actuator /refresh endpoint, services can reload config without restarting.

Useful for feature toggles, DB credentials, rate limits, etc.

📊 Benefits
Centralized management: One place to update configs for all services.

Environment separation: Different profiles for dev, test, prod.

Version control: Git history tracks changes.

Consistency: All services use the same source of truth.

Dynamic updates: No need to redeploy services for config changes.

🚨 Challenges & Considerations
Availability: Config Server must be highly available (often clustered).

Security: Sensitive configs (passwords, tokens) should be encrypted.

Bootstrap phase: Clients need Config Server up before they start.

Refresh strategy: Decide whether configs should auto-refresh or be triggered manually.

✅ Example Setup

Config Server application.yml

spring:
application:
name: config-server
cloud:
config:
server:
git:
uri: https://github.com/your-org/config-repo
clone-on-start: true
server:
port: 8888

Client Service bootstrap.yml

spring:
application:
name: user-service
cloud:
config:
uri: http://localhost:8888
Config Repo (Git)
Code
user-service.yml
hotel-service.yml
rating-service.yml
🔎 Key Difference from API Gateway
Config Server: Manages configuration for services.

API Gateway: Routes and filters requests between clients and services.

They often work together: Gateway itself fetches its routes and filters from Config Server, 
so you can change routing without redeploying.

what is the purpose of spring-cloud-config-server?

🎯 Main Purposes
1. Centralized Configuration
   Keeps all configuration files (YAML/Properties/JSON) in one place, usually a Git repository.

Every microservice fetches its configuration from the Config Server at startup.

2. Environment & Profile Management
   Supports multiple environments (dev, test, prod) and profiles.

Example: user-service-dev.yml vs user-service-prod.yml.

3. Dynamic Updates
   With @RefreshScope and Spring Actuator, services can reload configuration without restarting.

Useful for feature toggles, rate limits, DB credentials, etc.

4. Version Control & Audit
   Stores configs in Git, so every change is tracked.

Easy rollback to previous versions.

5. Consistency Across Services
   Ensures all services use the same configuration source.

Reduces duplication and configuration drift.

6. Security
   Sensitive values (passwords, tokens) can be encrypted in the repo.

Config Server decrypts them before sending to clients.

⚙️ How It Fits in Microservices
Config Server: Reads configs from Git.

Microservices (clients): Bootstraps by calling Config Server (http://config-server:8888/{application}/{profile}).

Service Registry + Gateway: They themselves can fetch configs from Config Server, so routing and discovery rules are externalized too.

✅ Example Flow
You push user-service.yml to Git.

Config Server pulls it.

User Service starts → contacts Config Server → loads its properties.

Later, you change DB URL in Git → Config Server serves new value → User Service refreshes without redeploy.

purpose of spring-cloud-config-server maven dependency:

The purpose of the spring-cloud-config-server Maven dependency is to turn a plain Spring Boot application into a Config Server.

the central service that manages and serves configuration properties to all your microservices.

🎯 What It Does

When you add:

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>


and annotate your main class with:

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
public static void main(String[] args) {
SpringApplication.run(ConfigServerApplication.class, args);
}
}

your application becomes a Config Server.

⚙️ Responsibilities of the Dependency:

Bootstraps Config Server Auto‑Configuration  
Provides all the beans and endpoints needed to serve configuration files.

Connects to External Config Sources  
Supports Git, SVN, file system, Vault, JDBC, etc.
Example: fetches user-service.yml from a Git repo.

Exposes REST Endpoints  
Clients can call endpoints like:

http://localhost:8888/{application}/{profile}
http://localhost:8888/user-service/dev

to retrieve their configuration.

Handles Profiles and Labels  
Supports multiple environments (dev, test, prod) and Git branches (main, release).

Integrates with Clients  
Works with spring-cloud-starter-config on the client side, so microservices automatically fetch configs at startup.

📊 Why It’s Needed
Without this dependency, your Spring Boot app is just a normal service.
With it, the app becomes a Config Server that:

Centralizes configuration management.

Provides version‑controlled configs via Git.

Enables dynamic refresh of properties.

Simplifies managing multiple environments.

what is the purpose of spring-cloud-starter-netflix-eureka-client?

if application.yml doesn't have any information, then this url http://localhost:8082/application/default

if we check the attribute propertySources has zero information

{
"name": "application",
"profiles": [
"default"
],
"label": null,
"version": "cd0a6494aba8e5c331d8f3ab8ef3d32fc45b3e09",
"state": "",
"propertySources": []
}

CASE1: information which has unique in all microservice instances like in user-service

this below information presents in every microservice instances

eureka:
   instance:
      hostname: localhost
      prefer-ip-address: false
      instance-id: ${spring.application.name}:${server.port}
      # prefer-ip-address: true # show  RiddhiNiddhi.mshome.net
   client:
      fetch-registry: true
      register-with-eureka: true
      service-url:
         defaultZone: http://localhost:8761/eureka

https://github.com/prabhatzgit/microservice-architecture-repo-config -> go to yml file and add the 
common properties and commit.

Now try the http://localhost:8082/application/default

{
"name": "application",
"profiles": [
"default"
],
"label": null,
"version": "63f1d583033ff9b1fa67d906a6826bab1655d56a",
"state": "",
"propertySources": [
{
"name": "https://github.com/prabhatzgit/microservice-architecture-repo-config/application.yml",
"source": {
"eureka.instance.hostname": "localhost",
"eureka.instance.prefer-ip-address": false,
"eureka.instance.instance-id": "${spring.application.name}:${server.port}",
"eureka.client.fetch-registry": true,
"eureka.client.register-with-eureka": true,
"eureka.client.service-url.defaultZone": "http://localhost:8761/eureka"
}
}
]
}

similarly, keep unique information in dev yml file and it visible on dev url

http://localhost:8082/application/dev

{
"name": "application",
"profiles": [
"dev"
],
"label": null,
"version": "d3cc62678bddc24535c2ef38e0afeb89f985d5f1",
"state": "",
"propertySources": [
{
"name": "https://github.com/prabhatzgit/microservice-architecture-repo-config/application-dev.yml",
"source": {
"spring.application.name": "config-server",
"eureka.instance.hostname": "localhost",
"eureka.instance.prefer-ip-address": false,
"eureka.instance.instance-id": "${spring.application.name}:${server.port}",
"eureka.client.fetch-registry": true,
"eureka.client.register-with-eureka": true,
"eureka.client.service-url.defaultZone": "http://localhost:8761/eureka"
}
},
{
"name": "https://github.com/prabhatzgit/microservice-architecture-repo-config/application.yml",
"source": {
"spring.application.name": "config-server",
"eureka.instance.hostname": "localhost",
"eureka.instance.prefer-ip-address": false,
"eureka.instance.instance-id": "${spring.application.name}:${server.port}",
"eureka.client.fetch-registry": true,
"eureka.client.register-with-eureka": true,
"eureka.client.service-url.defaultZone": "http://localhost:8761/eureka"
}
}
]
}