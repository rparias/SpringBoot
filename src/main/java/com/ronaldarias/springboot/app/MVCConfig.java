package com.ronaldarias.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.Paths;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        log.info(resourcePath);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);
    }
}
