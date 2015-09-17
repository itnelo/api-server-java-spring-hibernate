package api.DAO.Impl;

import api.util.HibernateUtil;
import org.hibernate.Session;

public abstract class DAOImpl
{
    protected Session session;

    protected Session openSession() {
        if (session == null) {
            session = openSessionImpl();
        }
        return session;
    }

    protected Session openSessionImpl() {
        return HibernateUtil.openSession();
    }

    protected void free() {
        if (session != null) {
            session.close();
            session = null;
        }
    }
}
