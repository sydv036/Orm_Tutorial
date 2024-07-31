package orm.thi_final_orm.service;

import orm.thi_final_orm.entities.Workker;

import java.util.List;

public interface IWorkkerService extends IService<Integer, Workker> {
    List<Workker> getAllWorkerByLevel(Integer level);

}
