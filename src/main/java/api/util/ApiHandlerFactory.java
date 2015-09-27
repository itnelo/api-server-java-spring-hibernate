package api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.classes.ApiException;
import api.classes.ApiQuery;
import api.handler.ApiHandler;

@Component
public class ApiHandlerFactory
{
    @Autowired private Translator tr;

    public final ApiHandler getHandler(final ApiQuery query) throws Exception {
        ApiHandler handler = null;
        String queryHandlerKey = null;
        try {
            queryHandlerKey = query.getHandlerKey();
            StringBuilder handlerNameBuilder = new StringBuilder("api.handler.version.v")
                    .append(query.getApiVersion())
                    .append(".")
                    .append(t(queryHandlerKey))
                    .append("Handler");
            handler = (ApiHandler) Class.forName(handlerNameBuilder.toString()).newInstance();
        } catch (ClassNotFoundException e) {
            throw new UnknownQueryException(tr.tr(Translator.CATEGORY.ERRORS, "Unknown request", queryHandlerKey));
        } catch (Exception e) {
            throw new ApiHandlerFactoryException(tr.tr(Translator.CATEGORY.ERRORS, "Handler initialization error"), e);
        }
        return handler;
    }

    private String t(String queryHandlerKey) {
        return queryHandlerKey.substring(0, 1).toUpperCase() + queryHandlerKey.substring(1).toLowerCase();
    }

    private class ApiHandlerFactoryException extends ApiException {
        public ApiHandlerFactoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private class UnknownQueryException extends ApiException {
        public UnknownQueryException(String message) {
            super(message);
        }
    }
}
