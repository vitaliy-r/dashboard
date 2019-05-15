package com.epam.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/swagger-ui.html");
    registry.addRedirectViewController("/swagger", "/swagger-ui.html");
    registry.addRedirectViewController("/docs", "/swagger-ui.html");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

}
