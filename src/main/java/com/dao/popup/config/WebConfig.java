package com.dao.popup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dao.popup.util.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String connectPath = "/image/**";
    private String resourcePath = "file:///"+Path.POPUP_IMAGE_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }

}
