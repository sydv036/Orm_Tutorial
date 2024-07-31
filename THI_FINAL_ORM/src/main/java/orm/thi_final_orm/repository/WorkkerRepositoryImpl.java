package orm.thi_final_orm.repository;

import org.hibernate.Session;
import orm.thi_final_orm.entities.Workker;
import orm.thi_final_orm.utils.HibernateConfig;

import java.util.List;

public class WorkkerRepositoryImpl implements IWorkkerRepository {

    private IRepositoryCommon<Integer, Workker> repositoryCommon = new RepositoryCommonImpl();
    private static final String SQL_GETBYID = "FROM Workker wk where wk.id = :id";

    @Override
    public List<Workker> getAllData() {
        return null;
    }

    @Override
    public Workker getByid(Integer id) {
        return repositoryCommon.getById(SQL_GETBYID, id);
    }

    @Override
    public Boolean saveOrUpdate(Workker obj) {
        return repositoryCommon.saveOrUpdate(obj);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<Workker> getAllWorkerByLevel(Integer level) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            String hql = "FROM Workker wk WHERE wk.level = :level";
            return session.createQuery(hql).setParameter("level", level).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
