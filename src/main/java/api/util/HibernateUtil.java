package api.util;

import java.util.Formatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

public class HibernateUtil {
    private static final ArrayList<Class> annotatedClasses;
    private static final HashMap<String, SessionFactory> sessionFactories;

    static {
        annotatedClasses = new ArrayList<Class>();
        sessionFactories = new HashMap<String, SessionFactory>(8, 0.75F);

        addAnnotatedClass(api.model.Client.class);
        addAnnotatedClass(api.model.Database.class);
        addAnnotatedClass(api.model.DatabaseUser.class);
    }

    public static Session openSession() {
        return openSession(null, null, null, null);
    }

    public static Session openSession(
                            final String dbhost,
                            final String dbname,
                            final String dbuser,
                            final String dbpass)
    {
        String key = new StringBuilder()
                .append(dbhost)
                .append(dbname)
                .append(dbuser)
                .append(dbpass)
                .toString();
        SessionFactory sf = sessionFactories.get(key);
        if (sf != null) {
            return sf.openSession();
        }
        try {
            sf = buildConfiguration(dbhost, dbname, dbuser, dbpass).buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
        sessionFactories.put(key, sf);
        return sf.openSession();
    }

    public static void addAnnotatedClass(Class c) {
        annotatedClasses.add(c);
    }

    private static Configuration buildConfiguration(
                                    final String dbhost,
                                    final String dbname,
                                    final String dbuser,
                                    final String dbpass) {

        Configuration cfg = new Configuration();
        annotatedClasses.forEach(cfg::addAnnotatedClass);
        if (dbuser != null) cfg.setProperty("hibernate.connection.username", dbuser);
        if (dbpass != null) cfg.setProperty("hibernate.connection.password", dbpass);
        String url = new Formatter().format(
                cfg.getProperty("hibernate.connection.url"),
                (dbhost != null) ? dbhost : cfg.getProperty("hibernate.connection.dbhost"),
                (dbname != null) ? dbname : cfg.getProperty("hibernate.connection.dbname")).toString();
        cfg.setProperty("hibernate.connection.url", url);
        return cfg;
    }
}
