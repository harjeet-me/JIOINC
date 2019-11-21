package com.jiotrasportinc.tms.repository;
import com.jiotrasportinc.tms.domain.LoadOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoadOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoadOrderRepository extends JpaRepository<LoadOrder, Long> {

}
