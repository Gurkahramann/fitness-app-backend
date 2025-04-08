package com.fitnesapp.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String mongoUri = System.getProperty("MONGO_URI");
        
        if (mongoUri == null || mongoUri.isBlank()) {
            throw new IllegalStateException("HATA: MONGO_URI could not find. Please check .env file");
        }

        return MongoClients.create(mongoUri);
    }
}