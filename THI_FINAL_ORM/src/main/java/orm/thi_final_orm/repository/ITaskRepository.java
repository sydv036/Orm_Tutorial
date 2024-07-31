package orm.thi_final_orm.repository;

import orm.thi_final_orm.entities.Task;

import java.util.List;

public interface ITaskRepository extends IRepository<Integer, Task> {
    List<Task> searchByName(String name);
}
