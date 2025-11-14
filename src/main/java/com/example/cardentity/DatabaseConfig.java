package com.example.cardentity;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StringUtils;

@Configuration
@EnableMongoRepositories
public class DatabaseConfig {

    @Value("${mongodb.user:}")
    private String mongoDbUser;

    @Value("${mongodb.password:}")
    private String mongoDbPassword;

    @Value("${mongodb.server:}")
    private String mongoDbServer;

    @Value("${mongodb.db:test}")
    private String mongoDb;

    @Value("${spring.data.mongodb.uri:}")
    private String springMongoUri;

    public @Bean MongoClient mongoClient() {
        if (StringUtils.hasText(springMongoUri)) {
            return MongoClients.create(springMongoUri);
        }
        String databaseName = resolveDatabaseName();
        if (!StringUtils.hasText(mongoDbServer)) {
            return MongoClients.create();
        }
        String credentials = StringUtils.hasText(mongoDbUser)
                ? mongoDbUser + ":" + mongoDbPassword + "@"
                : "";
        return MongoClients.create("mongodb://" + credentials + mongoDbServer + "/" + databaseName);
    }

    public @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, resolveDatabaseName());
    }

    private String resolveDatabaseName() {
        return StringUtils.hasText(mongoDb) ? mongoDb : "test";
    }
}