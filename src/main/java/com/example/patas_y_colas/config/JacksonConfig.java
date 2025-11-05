package com.example.patas_y_colas.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Module hibernate5JakartaModule() {
        // Este módulo hará que Jackson entienda los proxies de Hibernate sin forzar la carga perezosa
        Hibernate5JakartaModule module = new Hibernate5JakartaModule();
        // opcional: deshabilita la carga forzada de colecciones lazy
        module.disable(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING);
        return module;
    }
}
