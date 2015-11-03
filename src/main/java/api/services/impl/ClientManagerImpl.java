package api.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import api.services.PersistenceService;
import api.services.ClientManager;
import api.model.*;

import javax.persistence.EntityManager;

@Service("apiClientManager")
public class ClientManagerImpl
        implements ClientManager<Client>
{
    @Autowired @Qualifier("persistenceService")
    private PersistenceService persistenceService;

    private Client currentClient;

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client client) {
        currentClient = client;
        setClientEntityManager(currentClient);
    }

    public void setClientEntityManager(final Client client) {
        Database clientDatabase = client.getDatabase();
        DatabaseUser clientDatabaseUser = clientDatabase.getDatabaseUser();
        EntityManager entityManager = persistenceService.getEntityManager(
                null,
                clientDatabase.getDatabaseName(),
                clientDatabaseUser.getDatabaseUserName(),
                clientDatabaseUser.getDatabaseUserPass());
        client.setEntityManager(entityManager);
    }
}
