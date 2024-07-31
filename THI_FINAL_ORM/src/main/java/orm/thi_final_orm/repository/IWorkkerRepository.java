package orm.thi_final_orm.repository;

import orm.thi_final_orm.entities.Workker;

import java.util.List;

public interface IWorkkerRepository extends IRepository<Integer, Workker> {
    List<Workker> getAllWorkerByLevel(Integer level);
}
