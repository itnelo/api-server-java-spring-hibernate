package api.DAO;

import api.DAO.Impl.ClientDAOImpl;

public enum DAOFactory {
    INSTANCE;

    private static IClientDAO clientDAO;

    public IClientDAO getClientDAO() {
        if (clientDAO == null) {
            clientDAO = new ClientDAOImpl();
        }
        return clientDAO;
    }
}
