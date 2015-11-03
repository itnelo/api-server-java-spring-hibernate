package api.services.impl;

import org.springframework.stereotype.Service;
import api.services.PersistenceService;
import javax.persistence.*;
import java.util.*;

@Service("persistenceService")
public class PersistenceServiceImpl
        implements PersistenceService
{
    private static final HashMap<String, EntityManagerFactory> entityManagerFactories = new HashMap<>();

    static {
        entityManagerFactories.put("default", Persistence.createEntityManagerFactory("default"));
    }

    public EntityManager getEntityManager() {
        return entityManagerFactories.get("default").createEntityManager();
    }

    public EntityManager getEntityManager(
            final String dbhost,
            final String dbname,
            final String dbuser,
            final String dbpass) {
        EntityManagerFactory emf;
        if ((emf = entityManagerFactories.get("client")) != null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("client",
                buildConfiguration(dbhost, dbname, dbuser, dbpass));
        entityManagerFactories.put("client", emf);
        return emf.createEntityManager();
    }

    private Map<String, Object> buildConfiguration(
            final String dbhost,
            final String dbname,
            final String dbuser,
            final String dbpass) {
        Map<String, Object> defaultCfg = entityManagerFactories.get("default").getProperties();
        Map<String, Object> cfg = new HashMap<>();
        if (dbuser != null) cfg.put("javax.persistence.jdbc.user", dbuser);
        if (dbpass != null) cfg.put("javax.persistence.jdbc.password", dbpass);
        String url = new Formatter().format(
                (String)defaultCfg.get("javax.persistence.jdbc.url_template"),
                (dbhost != null) ? dbhost : (String)defaultCfg.get("javax.persistence.jdbc.default_host"),
                (dbname != null) ? dbname : (String)defaultCfg.get("javax.persistence.jdbc.default_database")).toString();
        cfg.put("javax.persistence.jdbc.url", url);
        return cfg;
    }

}
