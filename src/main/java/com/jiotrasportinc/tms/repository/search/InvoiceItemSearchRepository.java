package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.InvoiceItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InvoiceItem} entity.
 */
public interface InvoiceItemSearchRepository extends ElasticsearchRepository<InvoiceItem, Long> {
}
