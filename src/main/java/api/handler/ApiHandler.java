package api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import api.classes.*;
import api.model.Client;
import api.services.Translator;

@Component
public abstract class ApiHandler
        implements Handler<ApiRequest, Client>
{
    @Autowired @Qualifier("apiTranslator")
    protected Translator tr;

    @SuppressWarnings("unchecked")
    @Override
    public final String handle(ApiRequest request, Client client) throws ApiHandlerException {
        String result;
        try {
            result = run(request.getHandlerParams(), client.getEntityManager());
        } catch (Exception e) {
            Logger logger = Logger.getLogger("ApiHandler");
            logger.log(Level.INFO, request.toString());
            logger.log(Level.INFO, client.toString());
            throw new ApiHandlerException((String) tr.tr(Translator.CATEGORY.ERRORS, "Handler error", request.getQueryKey()), e);
        }
        return result;
    }

    abstract protected String run(Map<String, String> params, EntityManager entityManager);

    private class ApiHandlerException extends ApiException {
        public ApiHandlerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
