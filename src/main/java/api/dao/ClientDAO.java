package api.dao;

import api.model.Client;

public interface ClientDAO extends DAO<Client> {

    Client getByKey(final String clientKey);
    Client getByClientAndAccessKey(final String client_key, final String access_key);

}
