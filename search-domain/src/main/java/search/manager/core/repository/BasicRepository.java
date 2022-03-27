package search.manager.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import search.manager.core.entity.BasicEntity;

public interface BasicRepository extends JpaRepository<BasicEntity, Long>{

}
