package com.jiotrasportinc.tms.repository.search;
import com.jiotrasportinc.tms.domain.Invoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Invoice} entity.
 */
public interface InvoiceSearchRepository extends ElasticsearchRepository<Invoice, Long> {
}
