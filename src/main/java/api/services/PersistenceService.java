package api.services;

import javax.persistence.EntityManager;

public interface PersistenceService {

    EntityManager getEntityManager();

    /**
     *
     * У нас есть:
     *
     * 1. Главная БД, где хранится информация о клиентах (используется для авторизации)
     * 2. БД клиентов, с которыми происходит работа после авторизации
     *
     * Таким образом, после авторизации необходимо обратиться к другой БД,
     * для этого необходимо создать новый экземпляр EntityManagerFactory, изменив параметры:
     *
     * javax.persistence.jdbc.host
     * javax.persistence.jdbc.database
     * javax.persistence.jdbc.user
     * javax.persistence.jdbc.password
     *
     * Значения этих параметров мы получим в Runtime после успешной авторизации клиента
     *
     * Насколько я понял, создавать и хранить для каждого пользователя тяжелый объект EntityManagerFactory
     * будет очень затратно и неэффективно. Может быть, вариант с SessionFactory без дополнительных оберток
     * был не так уж и плох в данной ситуации?
     *
     */
    EntityManager getEntityManager(
            final String host,
            final String database,
            final String user,
            final String password);

}
