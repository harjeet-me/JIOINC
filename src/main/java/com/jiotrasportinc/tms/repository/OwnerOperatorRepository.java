package com.jiotrasportinc.tms.repository;
import com.jiotrasportinc.tms.domain.OwnerOperator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OwnerOperator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerOperatorRepository extends JpaRepository<OwnerOperator, Long> {

}
