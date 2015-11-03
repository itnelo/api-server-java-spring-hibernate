package api.handler;

public interface Handler<T,C> {

    String handle(T task, C client) throws Exception;

}
