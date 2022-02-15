package com.example.cardentity;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class DatabaseConfig {

    @Value("${mongodb.user}")
    private String mongoDbUser;

    @Value("${mongodb.password}")
    private String mongoDbPassword;

    @Value("${mongodb.server}")
    private String mongoDbServer;

    @Value("${mongodb.db}")
    private String mongoDb;

    public @Bean MongoClient mongoClient() {
        return MongoClients.create("mongodb://"+ mongoDbUser +":"+ mongoDbPassword +"@" + mongoDbServer);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDb);
    }
}