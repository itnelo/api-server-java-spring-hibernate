package api.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse {
    @Getter @Setter private int status;
    @Getter @Setter private String data;
    @Getter @Setter private String error;
}
