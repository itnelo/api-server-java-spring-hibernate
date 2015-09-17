package api.DAO;

import java.sql.SQLException;

public interface IDAO<T> {

    T getById(Long id) throws SQLException;

}
