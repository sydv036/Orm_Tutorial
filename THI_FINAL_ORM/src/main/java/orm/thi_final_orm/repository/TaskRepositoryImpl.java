package orm.thi_final_orm.repository;

import org.hibernate.Session;
import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.utils.HibernateConfig;

import java.util.List;

public class TaskRepositoryImpl implements ITaskRepository {
    private IRepositoryCommon<Integer, Task> repositoryCommon = new RepositoryCommonImpl();
    private static final String SQL_GETBYID = "FROM Task tk where tk.id = :id";

    @Override
    public List<Task> getAllData() {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            String hql = "FROM Task tk ";
            return session.createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Task getByid(Integer id) {
        return repositoryCommon.getById(SQL_GETBYID, id);
    }

    @Override
    public Boolean saveOrUpdate(Task obj) {
        return repositoryCommon.saveOrUpdate(obj);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<Task> searchByName(String name) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            String hql = "FROM Task tk WHERE tk.name like :name";
            return session.createQuery(hql).setParameter("name", "%" + name + "%").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
