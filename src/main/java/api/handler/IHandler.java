package api.handler;

import java.util.Map;

public interface IHandler<T,C> {

    Map<String, String> handle(final T task, final C client) throws Exception;

}
