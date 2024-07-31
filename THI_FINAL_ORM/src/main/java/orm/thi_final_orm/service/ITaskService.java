package orm.thi_final_orm.service;

import orm.thi_final_orm.entities.Task;

import java.util.List;

public interface ITaskService extends IService<Integer, Task> {
    List<Task> searchByName(String name);
}
