package api.DAO.Impl;

import org.hibernate.criterion.Restrictions;
import java.sql.SQLException;
import api.DAO.IClientDAO;
import api.model.Client;

public class ClientDAOImpl extends DAOImpl
        implements IClientDAO
{
    @Override
    public Client getById(Long client_id) throws SQLException {
        Client client = null;
        try {
            client = (Client) openSession().load(Client.class, client_id);
        } finally {
            free();
        }
        return client;
    }

    @Override
    public Client getByClientAndAccessKey(final String client_key, final String access_key) throws SQLException {
        Client client = null;
        try {
            client = (Client) openSession()
                                .createCriteria(Client.class)
                                .add(Restrictions.eq("clientKey", client_key))
                                .add(Restrictions.eq("accessKey", access_key))
                                .uniqueResult();
        } finally {
            free();
        }
        return client;
    }
}
