package api.handler.version.v1;

import api.handler.ApiHandler;
import org.hibernate.Session;
import java.util.Map;

public class StartupHandler extends ApiHandler {

    @Override
    protected Map<String, String> run(Map<String, String> params, Session clientSession) {
        return null;
    }

}
