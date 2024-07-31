package orm.thi_final_orm.service;

import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.entities.TaskAssignment;
import orm.thi_final_orm.entities.Workker;

public interface ITaskAssignmentService extends IService<Integer, TaskAssignment> {
    TaskAssignment getTaskAssignmentByTask(Task task);

    TaskAssignment getTaskAssignmentByWorkker(Workker workker);
}
