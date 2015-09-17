package api.util;

public interface IParser<T> {

    Object parse(final T data) throws Exception;

}
