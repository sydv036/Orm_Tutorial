package orm.thi_final_orm.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import orm.thi_final_orm.utils.HibernateConfig;

import java.util.List;

public class RepositoryCommonImpl implements IRepositoryCommon {

//    private Class<T> entityClass;

    @Override
    public List getAllData(String hql) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            Query query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object getById(String hql, Object id) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            Query query = session.createQuery(hql).setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean saveOrUpdate(Object obj) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteById(String hql, Object id) {
        Transaction transaction = null;
        Object obj = getById(hql, id);
        try (Session session = HibernateConfig.getFactory().openSession()) {
            if (obj != null) {
                transaction = session.beginTransaction();
                session.delete(obj);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public List getAllByCriteria() {
//        try (Session session = HibernateConfig.getFactory().openSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
//            Root<T> root = criteriaQuery.from(entityClass);
//            criteriaQuery.select(root);
//            Query<T> query = session.createQuery(criteriaQuery);
//            return query.getResultList();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
        return null;
    }

    public static void main(String[] args) {
//        Test test = Test.builder()
//                .name("Aaa")
//                .build();
////        System.out.println(new RepositoryCommonImpl().saveOrUpdate(test));
//        try (Session session = HibernateConfig.getFactory().openSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Test> criteriaQuery = criteriaBuilder.createQuery(Test.class);
//            Root<Test> root = criteriaQuery.from(Test.class);
////            criteriaQuery.select(root);
//            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"),1));
//            Query<Test> query = session.createQuery(criteriaQuery);
////            for (Test item : query.getResultList()) {
////                System.out.println(item);
////            }
//            System.out.println(query.getSingleResult());
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

}
