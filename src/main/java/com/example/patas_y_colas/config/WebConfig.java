package com.example.patas_y_colas.config;

// 1. IMPORTACIÓN AÑADIDA
import org.springframework.beans.factory.annotation.Value; 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 2. LEE LA VARIABLE DE ENTORNO "frontend.url"
    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permite CORS solo para nuestras rutas /api/
                
                // 3. USA LA VARIABLE DE RENDER
                .allowedOrigins(frontendUrl) 
                
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
//