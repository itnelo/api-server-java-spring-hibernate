package api.classes;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.annotation.Resource;
import lombok.Getter;
import java.util.Map;

@Component
@Configuration
@EnableAutoConfiguration
@ImportResource({"classpath:config.xml"})
public class ApiConfig {

    @Getter @Resource private Map<String, String> params;

}
