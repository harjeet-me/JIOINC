package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.LoadOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LoadOrder} entity.
 */
public interface LoadOrderSearchRepository extends ElasticsearchRepository<LoadOrder, Long> {
}
