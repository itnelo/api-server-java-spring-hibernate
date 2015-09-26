package api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hibernate.Session;
import api.DAO.DAOFactory;
import api.DAO.IClientDAO;
import api.classes.ApiException;
import api.classes.ApiQuery;
import api.model.Client;
import api.model.Database;
import api.model.DatabaseUser;

@Component
public class ApiClientManager
{
    @Autowired private Translator tr;

    public Client authorize(final ApiQuery query) throws ApiClientManagerException {
        Client client = null;
        try {
            IClientDAO clientDAO = DAOFactory.INSTANCE.getClientDAO();
            String client_key = query.getClientIdentifier();
            String access_key = query.getAccessKey();
            client = clientDAO.getByClientAndAccessKey(client_key, access_key);
            if (client == null)
                throw new ApiAccessDeniedException(
                        tr.tr(Translator.CATEGORY.ERRORS, "Access denied", client_key, access_key));
            startClientSession(client);
        } catch (Exception e) {
            throw new ApiClientManagerException(e);
        }
        return client;
    }

    private void startClientSession(final Client client) {
        Database clientDatabase = client.getDatabase();
        DatabaseUser clientDatabaseUser = clientDatabase.getDatabaseUser();
        Session session = HibernateUtil.openSession(
                                            null,
                                            clientDatabase.getDatabaseName(),
                                            clientDatabaseUser.getDatabaseUserName(),
                                            clientDatabaseUser.getDatabaseUserPass());
        client.setSession(session);
    }

    private class ApiClientManagerException extends ApiException {
        public ApiClientManagerException(Throwable cause) {
            super(cause);
        }
    }

    private class ApiAccessDeniedException extends ApiException {
        ApiAccessDeniedException(final String message) {
            super(message);
        }
    }
}
