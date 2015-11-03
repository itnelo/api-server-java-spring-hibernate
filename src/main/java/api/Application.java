package api;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import javax.annotation.Resource;
import java.util.Map;
import api.classes.SecurityConfig;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"api"})
@ImportResource({"classpath:config.xml"})
@Import({ SecurityConfig.class })
public class Application extends SpringBootServletInitializer {
    @Getter @Resource private Map<String, String> params;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
