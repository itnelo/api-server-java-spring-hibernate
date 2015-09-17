package api.handler;

import api.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import java.util.Map;

import api.classes.ApiException;
import api.classes.ApiQuery;
import api.util.ApiLogger;
import api.model.Client;

public abstract class ApiHandler
        implements IHandler<ApiQuery, Client>
{
    @Autowired protected ApiLogger logger;
    @Autowired protected Translator tr;

    @Override
    public final Map<String, String> handle(final ApiQuery query, final Client client) throws ApiHandlerException {
        Map<String, String> result = null;
        try {
            result = run(query.getHandlerParams(), client.getSession());
        } catch (Exception e) {
            logger.log(query.toString());
            logger.log(client.toString());
            throw new ApiHandlerException(tr.tr(Translator.CATEGORY.ERRORS, "Handler error", query.getHandlerKey()), e);
        }
        return result;
    }

    abstract protected Map<String, String> run(final Map<String, String> params, final Session clientSession);

    private class ApiHandlerException extends ApiException {
        public ApiHandlerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
