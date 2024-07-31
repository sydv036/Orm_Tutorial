package orm.thi_final_orm.service;

import java.util.List;

public interface IService<K, T> {
    List<T> getAllData();

    T getByid(K id);

    void saveOrUpdate(T obj);

    void deleteById(K id);
}
