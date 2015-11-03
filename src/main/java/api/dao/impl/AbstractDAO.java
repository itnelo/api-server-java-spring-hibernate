package api.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.persistence.EntityManager;
import api.services.PersistenceService;

public abstract class AbstractDAO
{
    @Autowired
    @Qualifier("persistenceService")
    protected PersistenceService persistenceService;
    protected EntityManager entityManager;

    protected final EntityManager entityManager() {
        if (entityManager == null) {
            entityManager = persistenceService.getEntityManager();
        }
        return entityManager;
    }

    protected void free() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
    }
}
