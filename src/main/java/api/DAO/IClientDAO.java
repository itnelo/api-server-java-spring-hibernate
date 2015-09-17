package api.DAO;

import api.model.Client;
import java.sql.SQLException;

public interface IClientDAO extends IDAO<Client> {

    Client getByClientAndAccessKey(final String client_key, final String access_key) throws SQLException;

}
