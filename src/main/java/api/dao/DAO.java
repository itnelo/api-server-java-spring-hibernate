package api.dao;

public interface DAO<T> {

    T getById(Long id);

}
