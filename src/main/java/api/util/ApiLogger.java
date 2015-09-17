package api.util;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

@Component
@ImportResource({"classpath:log.xml"})
public class ApiLogger {
    @Resource private Logger logger;

    public void log(final String[] info) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        for (String s: info) {
            sb.append(s + lineSeparator);
        }
        log(sb.toString());
    }

    public void log(final String info) {
        logger.info(info);
    }
}
