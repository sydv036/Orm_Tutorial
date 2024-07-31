package orm.thi_final_orm.repository;

import java.util.List;

public interface IRepository<K, T> {
    List<T> getAllData();

    T getByid(K id);

    Boolean saveOrUpdate(T obj);

    Boolean deleteById(K id);
}
