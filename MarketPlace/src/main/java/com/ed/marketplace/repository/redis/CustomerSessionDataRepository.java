package com.ed.marketplace.repository.redis;

import com.ed.marketplace.entity.CustomerSessionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSessionDataRepository extends CrudRepository<CustomerSessionData, String> {
}
