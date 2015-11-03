package api.dao.impl;

import org.springframework.stereotype.Component;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import api.dao.ClientDAO;
import api.model.Client;

@Component("clientDAO")
public class ClientDAOImpl extends AbstractDAO
        implements ClientDAO
{
    @Override
    public Client getById(Long clientId) {
        Client client = null;
        try {
            client = entityManager().find(Client.class, clientId);
        } catch (PersistenceException e) {

        } finally {
            free();
        }
        return client;
    }

    @Override
    public Client getByKey(String clientKey) {
        Client client = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            CriteriaQuery<Client> clientCriteria = criteriaBuilder.createQuery(Client.class);
            Root<Client> clientRoot = clientCriteria.from(Client.class);
            clientCriteria.select(clientRoot);
            clientCriteria.where(criteriaBuilder.equal(clientRoot.get("clientKey"), clientKey));
            client = entityManager().createQuery(clientCriteria).getSingleResult();
        } catch (PersistenceException e) {

        } finally {
            free();
        }
        return client;
    }

    @Override
    public Client getByClientAndAccessKey(final String clientKey, final String accessKey) {
        Client client = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            CriteriaQuery<Client> clientCriteria = criteriaBuilder.createQuery(Client.class);
            Root<Client> clientRoot = clientCriteria.from(Client.class);
            clientCriteria.select(clientRoot);
            clientCriteria.where(
                    criteriaBuilder.equal(clientRoot.get("clientKey"), clientKey),
                    criteriaBuilder.equal(clientRoot.get("accessKey"), accessKey));
            client = entityManager().createQuery(clientCriteria).getSingleResult();
        } catch (PersistenceException e) {

        }
        finally {
            free();
        }
        return client;
    }
}
