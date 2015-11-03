package api.services;

public interface ClientManager<T> {

    T getCurrentClient();
    void setCurrentClient(T client);
    void setClientEntityManager(T client);

}
