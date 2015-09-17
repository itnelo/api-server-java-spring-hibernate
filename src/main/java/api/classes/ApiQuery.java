package api.classes;

import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString
public class ApiQuery
{
    protected final Map<RequestParam, String> requestParams;
    protected final Map<String, String>       handlerParams;

    public enum RequestParam {
        CLIENT_IDENTIFIER,
        ACCESS_KEY,
        API_VERSION,
        API_HANDLER,
        QUERY_DATA
    }

    public ApiQuery(final Map<RequestParam, String> requestParams, final Map<String, String> handlerParams) {
        this.requestParams = new LinkedHashMap<>(requestParams);
        this.handlerParams = new LinkedHashMap<>(handlerParams);
    }

    public String getClientIdentifier() {
        return getRequestParam(RequestParam.CLIENT_IDENTIFIER);
    }

    public String getAccessKey() {
        return getRequestParam(RequestParam.ACCESS_KEY);
    }

    public String getHandlerKey() {
        return getRequestParam(RequestParam.API_HANDLER);
    }

    public String getApiVersion() {
        return getRequestParam(RequestParam.API_VERSION);
    }

    public String getRequestParam(final RequestParam param) {
        return requestParams.get(param);
    }

    public String getHandlerParam(final String paramName) {
        return handlerParams.get(paramName);
    }

    public Map<RequestParam, String> getRequestParams() {
        return new LinkedHashMap<>(requestParams);
    }

    public Map<String, String> getHandlerParams() {
        return new LinkedHashMap<>(handlerParams);
    }
}
