package api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.LinkedHashMap;
import api.classes.ApiException;
import api.classes.ApiQuery;

@Component
@ImportResource({"classpath:request.xml"})
public class ApiQueryParser
        implements IParser<HttpServletRequest>
{
    @Resource protected Map<String, String> apiRequestStructure;
    @Autowired private Translator tr;

    @Override
    public ApiQuery parse(final HttpServletRequest request) throws ApiQueryDataParseException {

        Map<ApiQuery.RequestParam, String> requestParams = new LinkedHashMap<ApiQuery.RequestParam, String>();

        for (ApiQuery.RequestParam requestParam: ApiQuery.RequestParam.values()) {
            String paramValue = request.getParameter(t(requestParam));
            requestParams.put(requestParam, paramValue);
        }

        Map<String, String> handlerParams = new LinkedHashMap<String, String>();
        String queryDataParamMatch = t(ApiQuery.RequestParam.QUERY_DATA) + "[";

        for (Map.Entry<String, String[]> entry: request.getParameterMap().entrySet()) {
            String name = entry.getKey();

            if (name.startsWith(queryDataParamMatch)) {
                String key = name.substring(name.indexOf('[') + 1, name.indexOf(']'));
                handlerParams.put(key, entry.getValue()[0]);
            }
        }

        return new ApiQuery(requestParams, handlerParams);
    }

    protected final String t(ApiQuery.RequestParam requestParam) {
        String t = apiRequestStructure.get(requestParam.toString());
        if (t == null)
            throw new ApiQueryDataParseException(
                    tr.tr(Translator.CATEGORY.ERRORS, "Unknown request param", requestParam.toString()));
        return t;
    }

    private class ApiQueryDataParseException
            extends ApiException
    {
        ApiQueryDataParseException(String message) {
            super(message);
        }
    }
}