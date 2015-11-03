package api.handler.version.v1;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Map;
import api.handler.ApiHandler;

public class StartupHandler extends ApiHandler {

    @SuppressWarnings("unchecked")
    @Override
    protected String run(Map<String, String> params, EntityManager entityManager) {
        Object[] res = (Object[])entityManager.createNativeQuery("SELECT * FROM setup").getSingleResult();
        return Arrays.toString(res);
    }

}
