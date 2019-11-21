package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Driver} entity.
 */
public interface DriverSearchRepository extends ElasticsearchRepository<Driver, Long> {
}
