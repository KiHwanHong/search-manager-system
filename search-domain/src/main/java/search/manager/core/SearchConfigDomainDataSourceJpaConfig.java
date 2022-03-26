package search.manager.core;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {SearchConfigDomainDataSourceJpaConfig.SEARCH_DOMAIN_PACKAGE},
    transactionManagerRef = SearchConfigDomainDataSourceJpaConfig.SEARCH_DOMAIN_TRANSACTION_MANAGER,
    entityManagerFactoryRef = SearchConfigDomainDataSourceJpaConfig.SEARCH_DOMAIN_ENTITY_MANAGER_FACTORY)
@Configuration
public class SearchConfigDomainDataSourceJpaConfig {

  public static final String SEARCH_DOMAIN_ENTITY_MANAGER_FACTORY = "searchDomainEntityManagerFactory";
  public static final String SEARCH_DOMAIN_JPA_PROPERTIES = "searchDomainJpaProperties";
  public static final String SEARCH_DOMAIN_HIBERNATE_PROPERTIES = "searchDomainHibernateProperties";
  public static final String SEARCH_DOMAIN_DATA_SOURCE = "searchDomainDataSource";
  public static final String SEARCH_DOMAIN_TRANSACTION_MANAGER = "searchDomainTransactionManager";
  public static final String SEARCH_DOMAIN_PERSIST_UNIT = "searchDomain";
  public static final String SEARCH_DOMAIN_PACKAGE = "search.manager.core";
  public static final String SEARCH_DOMAIN_JDBC_TEMPLATE = "searchDomainJdbcTemplate";


  @Bean(name = SEARCH_DOMAIN_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean searchDomainEntityManagerFactory() {
    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(),
        searchDomainJpaProperties().getProperties(), null)
        .dataSource(searchDomainDataSource())
        .properties(searchDomainHibernateProperties()
            .determineHibernateProperties(searchDomainJpaProperties().getProperties(),
                new HibernateSettings()))
        .persistenceUnit(SEARCH_DOMAIN_PERSIST_UNIT)
        .packages(
            SEARCH_DOMAIN_PACKAGE)
        .build();
  }

  @Bean(name = SEARCH_DOMAIN_JPA_PROPERTIES)
  @ConfigurationProperties(prefix = "search.domain.jpa")
  public JpaProperties searchDomainJpaProperties() {
    return new JpaProperties();
  }

  @Bean(name = SEARCH_DOMAIN_HIBERNATE_PROPERTIES)
  @ConfigurationProperties(prefix = "search.domain.jpa.hibernate")
  public HibernateProperties searchDomainHibernateProperties() {
    return new HibernateProperties();
  }

  @Bean
  @Qualifier(SEARCH_DOMAIN_DATA_SOURCE)
  @ConfigurationProperties("search.domain.datasource")
  public DataSource searchDomainDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = SEARCH_DOMAIN_TRANSACTION_MANAGER)
  public PlatformTransactionManager searchDomainTransactionManager(
      @Autowired @Qualifier(SEARCH_DOMAIN_ENTITY_MANAGER_FACTORY) EntityManagerFactory searchDomainEntityManagerFactory) {
    return new JpaTransactionManager(searchDomainEntityManagerFactory);
  }

  @Bean(name = SEARCH_DOMAIN_JDBC_TEMPLATE)
  public JdbcTemplate certificateDomainJdbcTemplate(
      @Qualifier("searchDomainDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}