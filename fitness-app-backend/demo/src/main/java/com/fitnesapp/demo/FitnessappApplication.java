package com.fitnesapp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class FitnessappApplication {
    
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.out.println("dotenv: " + dotenv);
        String pgUser = dotenv.get("POSTGRES_USER");
        if (pgUser != null) System.setProperty("POSTGRES_USER", pgUser);

        String pgPassword = dotenv.get("POSTGRES_PASSWORD");
        if (pgPassword != null) System.setProperty("POSTGRES_PASSWORD", pgPassword);

        String pgDb = dotenv.get("POSTGRES_DB");
        if (pgDb != null) System.setProperty("POSTGRES_DB", pgDb);

        String pgHost = dotenv.get("POSTGRES_HOST");
        if (pgHost != null) System.setProperty("POSTGRES_HOST", pgHost);

        String pgPort = dotenv.get("POSTGRES_PORT");
        if (pgPort != null) System.setProperty("POSTGRES_PORT", pgPort);
        String nodeAuthServiceUrl = dotenv.get("NODE_JS_AUTH_SERVICE_URL");
        if (nodeAuthServiceUrl != null) System.setProperty("NODE_JS_AUTH_SERVICE_URL", nodeAuthServiceUrl);
        String credentialsPath = "acquired-device-460615-h3-85a7ca15256b.json";
        java.io.File f = new java.io.File(credentialsPath);
        System.out.println("Dosya var mı? " + f.getAbsolutePath() + " -> " + f.exists());
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", f.getAbsolutePath());        
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
        
        SpringApplication.run(FitnessappApplication.class, args);
    }
}