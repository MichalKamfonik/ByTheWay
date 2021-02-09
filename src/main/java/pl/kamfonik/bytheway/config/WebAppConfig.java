package pl.kamfonik.bytheway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.kamfonik.bytheway.converter.ActivityConverter;
import pl.kamfonik.bytheway.converter.CategoryConverter;
import pl.kamfonik.bytheway.converter.DurationConverter;
import pl.kamfonik.bytheway.converter.PlaceConverter;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/login");
        registry.addViewController("/403").setViewName("login/403");
    }

    @Bean
    public CategoryConverter getCategoryConverter(){
        return new CategoryConverter();
    }
    @Bean
    public DurationConverter getDurationConverter(){
        return new DurationConverter();
    }
    @Bean
    public PlaceConverter getPlaceConverter(){
        return new PlaceConverter();
    }
    @Bean
    public ActivityConverter getActivityConverter(){
        return new ActivityConverter();
    }
}
