package api.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import java.util.Arrays;
import java.util.logging.*;
import api.services.*;
import api.classes.*;
import api.handler.Handler;
import api.model.Client;

@Service("apiDispatcher")
public class DispatcherImpl
        implements Dispatcher<ApiRequest, ApiResponse>
{
    @Autowired
    @Qualifier("apiClientManager")
    private ClientManager clientManager;

    @Autowired
    @Qualifier("apiHandlerFactory")
    private HandlerFactory handlerFactory;

    @Autowired
    @Qualifier("apiTranslator")
    private Translator tr;

    @SuppressWarnings("unchecked")
    public ApiResponse process(ApiRequest request) {
        ApiResponse response = new ApiResponse();
        try {
            Client client = (Client) clientManager.getCurrentClient();
            Handler handler = (Handler) handlerFactory.getHandler(request.getQueryKey());
            response.setData(handler.handle(request, client));
            response.setStatus(1);
        } catch (Exception e) {
            Logger.getLogger("DispatcherImpl").log(Level.INFO, Arrays.toString(ExceptionUtils.getRootCauseStackTrace(e)));
            response.setStatus(0);
            response.setError((String) tr.tr(Translator.CATEGORY.ERRORS, "Internal error"));
        }
        return response;
    }
}
