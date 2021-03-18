package pl.kamfonik.bytheway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.kamfonik.bytheway.converter.CategoryConverter;
import pl.kamfonik.bytheway.converter.PlaceConverter;

import java.util.Locale;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/login");
        registry.addViewController("/403").setViewName("login/403");
        registry.addViewController("/").setViewName("home");
    }

    @Bean
    public CategoryConverter getCategoryConverter(){
        return new CategoryConverter();
    }
    @Bean
    public PlaceConverter getPlaceConverter(){
        return new PlaceConverter();
    }

    @Bean(name="localeResolver")
    public LocaleContextResolver getLocaleContextResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
