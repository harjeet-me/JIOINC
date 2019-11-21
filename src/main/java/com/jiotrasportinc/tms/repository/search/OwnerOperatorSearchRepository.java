package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.OwnerOperator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OwnerOperator} entity.
 */
public interface OwnerOperatorSearchRepository extends ElasticsearchRepository<OwnerOperator, Long> {
}
