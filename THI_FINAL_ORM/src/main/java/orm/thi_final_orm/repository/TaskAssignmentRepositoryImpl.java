package orm.thi_final_orm.repository;

import org.hibernate.Session;
import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.entities.TaskAssignment;
import orm.thi_final_orm.entities.Workker;
import orm.thi_final_orm.utils.HibernateConfig;

import java.util.List;

public class TaskAssignmentRepositoryImpl implements ITaskAssignmentRepository {

    private IRepositoryCommon<Integer, TaskAssignment> repositoryCommon = new RepositoryCommonImpl();

    @Override
    public List<TaskAssignment> getAllData() {
        return null;
    }

    @Override
    public TaskAssignment getByid(Integer id) {
        return null;
    }

    @Override
    public Boolean saveOrUpdate(TaskAssignment obj) {
        return repositoryCommon.saveOrUpdate(obj);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public TaskAssignment getTaskAssignmentByTask(Task task) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            String hql = " FROM TaskAssignment tka where tka.task = :task";
            return session.createQuery(hql, TaskAssignment.class).setParameter("task", task).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TaskAssignment getTaskAssignmentByWorkker(Workker workker) {
        try (Session session = HibernateConfig.getFactory().openSession()) {
            String hql = " FROM TaskAssignment tka where tka.workker = :workker";
            return session.createQuery(hql, TaskAssignment.class).setParameter("workker", workker).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private static ITaskRepository taskRepository = new TaskRepositoryImpl();

    public static void main(String[] args) {
        Task task = taskRepository.getByid(2);
        System.out.println(new TaskAssignmentRepositoryImpl().getTaskAssignmentByTask(task));
    }
}
