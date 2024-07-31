package orm.thi_final_orm.repository;

import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.entities.TaskAssignment;
import orm.thi_final_orm.entities.Workker;

public interface ITaskAssignmentRepository extends IRepository<Integer, TaskAssignment> {
    TaskAssignment getTaskAssignmentByTask(Task task);

    TaskAssignment getTaskAssignmentByWorkker(Workker workker);
}
