package com.ronaldarias.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

//    private final Logger log = LoggerFactory.getLogger(getClass());

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);
//
//        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//
//        log.info(resourcePath);
//
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations(resourcePath);
//    }
}
