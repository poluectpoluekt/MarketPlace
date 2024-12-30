package com.ed.marketplace.repository.redis;

import com.ed.marketplace.app_class.CustomerSessionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSessionDataRepository extends CrudRepository<CustomerSessionData, String> {
}
