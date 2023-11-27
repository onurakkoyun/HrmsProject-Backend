package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.LanguageList;

public interface ILanguageListDao extends JpaRepository<LanguageList, Long>{

}
