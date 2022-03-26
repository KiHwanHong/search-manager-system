package search.manager.core;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchQueryDslConfig {

  @PersistenceContext(unitName = SearchConfigDomainDataSourceJpaConfig.SEARCH_DOMAIN_PERSIST_UNIT)
  private EntityManager certificateEntityManager;

  @Bean
  public JPAQueryFactory certificateJpaQueryFactory() {
    return new JPAQueryFactory(certificateEntityManager);
  }
}
