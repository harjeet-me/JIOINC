package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Customer} entity.
 */
public interface CustomerSearchRepository extends ElasticsearchRepository<Customer, Long> {
}
