package api.services;

public interface Dispatcher<I,O> {

    O process(I request);

}
