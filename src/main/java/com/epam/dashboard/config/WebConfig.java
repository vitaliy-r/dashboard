package com.epam.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private static final String SWAGGER_UI_URI = "/swagger-ui.html";

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", SWAGGER_UI_URI);
    registry.addRedirectViewController("/swagger", SWAGGER_UI_URI);
    registry.addRedirectViewController("/docs", SWAGGER_UI_URI);
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

}
