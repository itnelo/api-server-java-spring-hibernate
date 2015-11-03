package api.services.impl;

import org.springframework.stereotype.Service;
import api.services.HandlerFactory;
import api.handler.ApiHandler;
import api.handler.version.v1.*;

@Service("apiHandlerFactory")
public class HandlerFactoryImpl
        implements HandlerFactory<ApiHandler>
{
    enum HandlerType {
        STARTUP
    }

    public ApiHandler getHandler(String handlerType) {
        switch (HandlerType.valueOf(handlerType.toUpperCase())) {
            case STARTUP: return new StartupHandler();
            default: return null;
        }
    }
}
