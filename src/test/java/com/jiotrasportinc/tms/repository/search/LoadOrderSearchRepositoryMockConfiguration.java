package com.jiotrasportinc.tms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LoadOrderSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LoadOrderSearchRepositoryMockConfiguration {

    @MockBean
    private LoadOrderSearchRepository mockLoadOrderSearchRepository;

}
