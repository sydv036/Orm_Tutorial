package orm.thi_final_orm.service;

import orm.thi_final_orm.common.ValidationCommon;
import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.repository.ITaskRepository;
import orm.thi_final_orm.repository.TaskRepositoryImpl;

import java.util.List;

public class TaskServiceImpl implements ITaskService {

    private ITaskRepository taskRepository = new TaskRepositoryImpl();

    @Override
    public List<Task> getAllData() {
        return taskRepository.getAllData();
    }

    @Override
    public Task getByid(Integer id) {
        return taskRepository.getByid(id);
    }

    @Override
    public void saveOrUpdate(Task obj) {
        Boolean isCheck = taskRepository.saveOrUpdate(obj);
        ValidationCommon.checkProcess(isCheck);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Task> searchByName(String name) {
        return taskRepository.searchByName(name);
    }
}
