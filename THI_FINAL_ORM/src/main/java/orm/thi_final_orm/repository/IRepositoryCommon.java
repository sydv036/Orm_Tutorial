package orm.thi_final_orm.repository;

import java.util.List;

public interface IRepositoryCommon<K, T> {

    List<T> getAllData(String hql);

    T getById(String hql, K id);

    Boolean saveOrUpdate(T obj);

    Boolean deleteById(String hql, K id);

}
