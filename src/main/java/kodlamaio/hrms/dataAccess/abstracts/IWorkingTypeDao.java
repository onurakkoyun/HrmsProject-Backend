package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.WorkingType;

public interface IWorkingTypeDao extends JpaRepository<WorkingType, Integer>{

}
