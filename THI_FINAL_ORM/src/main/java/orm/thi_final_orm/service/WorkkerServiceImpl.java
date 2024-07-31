package orm.thi_final_orm.service;

import orm.thi_final_orm.common.ValidationCommon;
import orm.thi_final_orm.entities.Workker;
import orm.thi_final_orm.repository.IWorkkerRepository;
import orm.thi_final_orm.repository.WorkkerRepositoryImpl;

import java.util.List;

public class WorkkerServiceImpl implements IWorkkerService {

    private IWorkkerRepository workkerRepository = new WorkkerRepositoryImpl();

    @Override
    public List<Workker> getAllData() {
        return null;
    }

    @Override
    public Workker getByid(Integer id) {
        return workkerRepository.getByid(id);
    }

    @Override
    public void saveOrUpdate(Workker obj) {
        Boolean isCheck = workkerRepository.saveOrUpdate(obj);
        ValidationCommon.checkProcess(isCheck);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Workker> getAllWorkerByLevel(Integer level) {
        return workkerRepository.getAllWorkerByLevel(level);
    }
}
