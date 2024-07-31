package orm.thi_final_orm.service;

import orm.thi_final_orm.common.ValidationCommon;
import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.entities.TaskAssignment;
import orm.thi_final_orm.entities.Workker;
import orm.thi_final_orm.repository.ITaskAssignmentRepository;
import orm.thi_final_orm.repository.TaskAssignmentRepositoryImpl;

import java.util.List;

public class TaskAssignmentServiceImpl implements ITaskAssignmentService {

    private ITaskAssignmentRepository taskAssignmentRepository = new TaskAssignmentRepositoryImpl();

    @Override
    public List<TaskAssignment> getAllData() {
        return null;
    }

    @Override
    public TaskAssignment getByid(Integer id) {
        return null;
    }

    @Override
    public void saveOrUpdate(TaskAssignment obj) {
        Boolean isCheck = taskAssignmentRepository.saveOrUpdate(obj);
        ValidationCommon.checkProcess(isCheck);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public TaskAssignment getTaskAssignmentByTask(Task task) {
        return taskAssignmentRepository.getTaskAssignmentByTask(task);
    }

    @Override
    public TaskAssignment getTaskAssignmentByWorkker(Workker workker) {
        return taskAssignmentRepository.getTaskAssignmentByWorkker(workker);
    }
}
